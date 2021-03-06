package org.apache.synapse.protocol.kafka;

import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.inbound.InjectHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KAFKAMessageListener extends AbstractKafkaMessageListener {

	public KAFKAMessageListener(int threadCount, List<String> topics,
			Properties kafkaProperties, InjectHandler injectHandler)
			throws Exception {
		this.threadCount = threadCount;
		this.topics = topics;
		this.kafkaProperties = kafkaProperties;
		this.injectHandler = injectHandler;

	}

	public boolean createKafkaConsumerConnector() throws Exception {

		boolean isCreated = false;
		try {
			if (consumerConnector == null) {
				logger.info("Creating Kafka Consumer Connector...");
				consumerConnector = Consumer
						.createJavaConsumerConnector(new ConsumerConfig(
								kafkaProperties));
				logger.info("Kafka Consumer Connector is created");
				start();

			}
			isCreated = true;
		} catch (ZkTimeoutException toe) {
			logger.error(" Error in Creating Kafka Consumer Connector | ZkTimeout"
					+ toe.getMessage());
			throw new Exception(
					" Error in Creating Kafka Consumer Connector| ZkTimeout",
					toe);

		} catch (Exception e) {
			logger.error(" Error in Creating Kafka Consumer Connector "

			+ e.getMessage());
			throw new Exception(" Error in Creating Kafka Consumer Connector ",
					e);
		}
		return isCreated;
	}

	/*
	 * Starts topics consuming
	 */

	public void start() throws Exception {
		try {
			logger.info("Starting KAFKA consumer listener...");
			Map<String, Integer> topicCount = new HashMap<String, Integer>();

			if (topics != null && topics.size() > 0) {
				// Define #threadCount thread/s for topic
				for (String topic : topics) {
					topicCount.put(topic, threadCount);
				}
				Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumerConnector
						.createMessageStreams(topicCount);
				for (String topic : topics) {
					List<KafkaStream<byte[], byte[]>> streams = consumerStreams
							.get(topic);
					startConsumers(streams);

				}
			} else if (kafkaProperties.getProperty(KAFKAConstants.TOPIC_FILTER) != null) {
				// Define #threadCount thread/s for topic filter
				List<KafkaStream<byte[], byte[]>> consumerStreams;
				boolean isFromWhitelist = (kafkaProperties
						.getProperty(KAFKAConstants.FILTER_FROM_WHITELIST) == null || kafkaProperties
						.getProperty(KAFKAConstants.FILTER_FROM_WHITELIST)
						.isEmpty()) ? Boolean.TRUE
						: Boolean
								.parseBoolean(kafkaProperties
										.getProperty(KAFKAConstants.FILTER_FROM_WHITELIST));
				if (isFromWhitelist) {
					consumerStreams = consumerConnector
							.createMessageStreamsByFilter(
									new Whitelist(
											kafkaProperties
													.getProperty(KAFKAConstants.TOPIC_FILTER)),
									threadCount);
				} else {
					consumerStreams = consumerConnector
							.createMessageStreamsByFilter(
									new Blacklist(
											kafkaProperties
													.getProperty(KAFKAConstants.TOPIC_FILTER)),
									threadCount);
				}

				startConsumers(consumerStreams);
			}

		} catch (Exception e) {
			logger.error("Error while Starting KAFKA consumer listener "
					+ e.getMessage());
			throw new Exception(
					"Error while Starting KAFKA consumer listener ", e);
		}
	}

	protected void startConsumers(List<KafkaStream<byte[], byte[]>> streams) {
		for (KafkaStream<byte[], byte[]> stream : streams) {
			consumerIte = stream.iterator();
			break; // we only use one stream as per suggested in the review
		}
	}

	@Override
	public void injectMessageToESB() {
		byte[] msg = consumerIte.next().message();
		injectHandler.invoke(msg);
	}

	@Override
	public boolean hasNext() {
		return consumerIte.hasNext();
	}
}
