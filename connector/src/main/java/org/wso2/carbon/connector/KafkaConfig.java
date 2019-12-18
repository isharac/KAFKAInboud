package org.wso2.carbon.connector;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.connector.core.*;

public class KafkaConfig extends AbstractConnector {

	public void connect(MessageContext messageContext) throws ConnectException {

		Logger LOG = LoggerFactory.getLogger(KafkaProduce.class);

		Properties props = new Properties();
		SynapseLog log = getLog(messageContext);
		log.auditLog("Start : Config Kafka Broker list");

		String brokerList = KafkaUtils.lookupTemplateParamater(messageContext,
				KafkaConnectConstants.PARAM_BROKER_LIST);
		String serializationClass = KafkaUtils
				.lookupTemplateParamater(messageContext,
						KafkaConnectConstants.PARAM_SERIALIZATION_CLASS);
		String requiredAcks = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_REQUIRED_ACKS);

		String producerType = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_PRODUCER_TYPE);

		String compressionType = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_COMPRESSION_TYPE);

		String keySerializerClass = KafkaUtils.lookupTemplateParamater(
				messageContext,
				KafkaConnectConstants.PARAM_KEY_SERIALIZER_CLASS);

		String partitionerClass = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_PARTITION_CLASS);

		String compressedTopic = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_COMPRESSED_TOPIC);

		String messageSendMaxRetries = KafkaUtils.lookupTemplateParamater(
				messageContext,
				KafkaConnectConstants.PARAM_MESSAGE_SEND_MAX_RETRIES);

		String retryBackOff = KafkaUtils.lookupTemplateParamater(
				messageContext,
				KafkaConnectConstants.PARAM_TIME_REFRESH_METADATA);

		String refreshIntervals = KafkaUtils.lookupTemplateParamater(
				messageContext,
				KafkaConnectConstants.PARAM_TIME_REFRESH_METADATA_AFTER_TOPIC);

		String bufferMaxMessages = KafkaUtils.lookupTemplateParamater(
				messageContext,
				KafkaConnectConstants.PARAM_BUFFRING_MAX_MESSAGES);

		String batchNoMessages = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_BATCH_NO_MESSAGES);

		String sendBufferSize = KafkaUtils.lookupTemplateParamater(
				messageContext, KafkaConnectConstants.PARAM_SEND_BUFFER_SIZE);

		try {

			props.put(KafkaConnectConstants.BROKER_LIST, brokerList.toString());

			if (serializationClass != null) {
				props.put(KafkaConnectConstants.SERIALIZATION_CLASS,
						serializationClass);
			} else {

				props.put(KafkaConnectConstants.SERIALIZATION_CLASS,
						KafkaConnectConstants.DEFAULT_SERIALIZATION_CLASS);
			}

			if (requiredAcks != null) {
				props.put(KafkaConnectConstants.REQUIRED_ACKS, requiredAcks);
			} else {
				props.put(KafkaConnectConstants.REQUIRED_ACKS,
						KafkaConnectConstants.DEFAULT_REQUIRED_ACKS);
			}

			if (producerType != null) {
				props.put(KafkaConnectConstants.PRODUCER_TYPE, producerType);
			}

			else {

				props.put(KafkaConnectConstants.PRODUCER_TYPE,
						KafkaConnectConstants.DEFAULT_PRODUCER_TYPE);
			}

			if (compressionType != null) {
				props.put(KafkaConnectConstants.COMPRESSION_TYPE,
						compressionType);
			} else {

				props.put(KafkaConnectConstants.COMPRESSION_TYPE,
						KafkaConnectConstants.DEFAULT_COMPRESSION_TYPE);
			}

			if (keySerializerClass != null) {
				props.put(KafkaConnectConstants.KEY_SERIALIZER_CLASS,
						keySerializerClass);
			} else {

				props.put(KafkaConnectConstants.KEY_SERIALIZER_CLASS,
						KafkaConnectConstants.DEFAULT_KEY_SERIALIZER_CLASS);
			}

			if (partitionerClass != null) {
				props.put(KafkaConnectConstants.PARTITION_CLASS,
						partitionerClass);
			} else {

				props.put(KafkaConnectConstants.PARTITION_CLASS,
						KafkaConnectConstants.DEFAULT_PARTITION_CLASS);
			}

			if (compressedTopic != null) {
				props.put(KafkaConnectConstants.COMPRESSED_TOPIC,
						compressedTopic);
			} else {

				props.put(KafkaConnectConstants.COMPRESSED_TOPIC,
						KafkaConnectConstants.DEFAULT_COMPRESSED_TOPIC);
			}

			if (messageSendMaxRetries != null) {
				props.put(KafkaConnectConstants.MESSAGE_SEND_MAX_RETRIES,
						messageSendMaxRetries);
			} else {

				props.put(KafkaConnectConstants.MESSAGE_SEND_MAX_RETRIES,
						KafkaConnectConstants.DEFAULT_MESSAGE_SEND_MAX_RETRIES);
			}

			if (retryBackOff != null) {
				props.put(KafkaConnectConstants.TIME_REFRESH_METADATA,
						retryBackOff);
			} else {

				props.put(KafkaConnectConstants.TIME_REFRESH_METADATA,
						KafkaConnectConstants.DEFAULT_TIME_REFRESH_METADATA);
			}

			if (refreshIntervals != null) {
				props.put(
						KafkaConnectConstants.TIME_REFRESH_METADTA_AFTER_TOPIC,
						refreshIntervals);
			} else {

				props.put(
						KafkaConnectConstants.TIME_REFRESH_METADTA_AFTER_TOPIC,
						KafkaConnectConstants.DEFAULT_TIME_REFRESH_METADATA_AFTER_TOPIC);
			}

			if (bufferMaxMessages != null) {
				props.put(KafkaConnectConstants.BUFFER_MAX_MESSAGES,
						bufferMaxMessages);
			} else {

				props.put(KafkaConnectConstants.BUFFER_MAX_MESSAGES,
						KafkaConnectConstants.DEFAULT_BUFFER_MAX_MESSAGE);
			}

			if (batchNoMessages != null) {
				props.put(KafkaConnectConstants.NO_MESSAAGE_BATCHED_PRODUCER,
						batchNoMessages);
			} else {

				props.put(
						KafkaConnectConstants.NO_MESSAAGE_BATCHED_PRODUCER,
						KafkaConnectConstants.DEFAULT_NO_MESSAAGE_BATCHED_PRODUCER);
			}

			if (sendBufferSize != null) {
				props.put(KafkaConnectConstants.BUFFER_SIZE, sendBufferSize);
			} else {

				props.put(KafkaConnectConstants.BUFFER_SIZE,
						KafkaConnectConstants.DEFAULT_BUFFER_SIZE);
			}

			try {

				new Producer<String, String>(new ProducerConfig(props));

			} catch (Exception e) {
				LOG.error(
						"Failed to load properties from file {}, exiting: {}",
						e.getMessage());

			}

		} catch (Exception e) {
			throw new ConnectException(e);
		}

	}

}
