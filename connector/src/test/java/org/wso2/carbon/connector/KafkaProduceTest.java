//package org.wso2.carbon.connector;
//
//import java.util.Properties;
//
//import junit.framework.TestCase;
//import kafka.javaapi.producer.Producer;
//import kafka.producer.ProducerConfig;
//
//public class KafkaProduceTest extends TestCase {
//
//	public static String message1 = "{\"topic\":\"my_topic\",\"message\":\"my sample message\"}";
//
//	public void testConnection() throws Exception {
//
//		Properties props = getProperties("localhost:9092",
//				"kafka.serializer.StringEncoder", "1", "sync");
//		Producer<String, String> producer = new Producer<String, String>(
//				new ProducerConfig(props));
//		KafkaProduce.send(producer, "mytopic", "", message1);
//	}
//
//	private Properties getProperties(String brokerlist,
//			String serializationClass, String requiredAcks, String producerType) {
//		Properties props = new Properties();
//		props.put("metadata.broker.list", brokerlist);
//		props.put("serializer.class", serializationClass);
//		props.put("request.required.acks", requiredAcks);
//		props.put("producer.type", producerType);
//
//		return props;
//	}
//
//}