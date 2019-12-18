package org.wso2.carbon.connector;

public class KafkaConnectConstants {

	// Configuration properties keys

	public static final String BROKER_LIST = "metadata.broker.list";

	public static final String REQUIRED_ACKS = "request.required.acks";

	public static final String PRODUCER_TYPE = "producer.type";

	public static final String SERIALIZATION_CLASS = "serializer.class";

	public static final String KEY_SERIALIZER_CLASS = "key.serializer.class";

	public static final String PARTITION_CLASS = "partitioner.class";

	public static final String COMPRESSION_TYPE = "compression.codec";

	public static final String COMPRESSED_TOPIC = "compressed.topics";

	public static final String MESSAGE_SEND_MAX_RETRIES = "message.send.max.retries";

	public static final String TIME_REFRESH_METADATA = "retry.backoff.ms";

	public static final String TIME_REFRESH_METADTA_AFTER_TOPIC = "topic.metadata.refresh.interval.ms";

	public static final String BUFFER_MAX_TIME = "queue.buffering.max.ms";

	public static final String BUFFER_MAX_MESSAGES = "queue.buffering.max.messages";

	public static final String NO_MESSAAGE_BATCHED_PRODUCER = "batch.num.messages";

	public static final String BUFFER_SIZE = "send.buffer.bytes";

	// Configuration properties parameter

	public static final String PARAM_BROKER_LIST = "brokerlist";

	public static final String PARAM_SERIALIZATION_CLASS = "serializationclass";

	public static final String PARAM_PRODUCER_TYPE = "producertype";

	public static final String PARAM_REQUIRED_ACKS = "requiredacks";

	public static final String PARAM_TOPIC = "topic";

	public static final String PARAM_MESSAGE = "message";

	public static final String PARAM_KEY = "key";

	public static final String PARAM_KEY_SERIALIZER_CLASS = "keyserializerclass";

	public static final String PARAM_PARTITION_CLASS = "partitionerclass";

	public static final String PARAM_COMPRESSION_TYPE = "compressioncodec";

	public static final String PARAM_COMPRESSED_TOPIC = "compressedtopics";

	public static final String PARAM_MESSAGE_SEND_MAX_RETRIES = "messagesendmaxretries";

	public static final String PARAM_TIME_REFRESH_METADATA = "retrybackoff";

	public static final String PARAM_TIME_REFRESH_METADATA_AFTER_TOPIC = "refreshinterval";

	public static final String PARAM_BUFFRING_MAX_MESSAGES = "bufferingmaxmessages";

	public static final String PARAM_BATCH_NO_MESSAGES = "batchnomessages";

	public static final String PARAM_SEND_BUFFER_SIZE = "sendbuffersize";

	// Configuration properties default values

	public static final String DEFAULT_REQUIRED_ACKS = "0";

	public static final String DEFAULT_SERIALIZATION_CLASS = "kafka.serializer.StringEncoder";

	public static final String DEFAULT_PRODUCER_TYPE = "sync";

	public static final String DEFAULT_KEY_SERIALIZER_CLASS = "0";

	public static final String DEFAULT_PARTITION_CLASS = "kafka.producer.DefaultPartitioner";

	public static final String DEFAULT_COMPRESSION_TYPE = "none";

	public static final String DEFAULT_COMPRESSED_TOPIC = null;

	public static final String DEFAULT_TIME_REFRESH_METADATA = "60000";

	public static final String DEFAULT_TIME_REFRESH_METADATA_AFTER_TOPIC = "";

	public static final String DEFAULT_BUFFER_MAX_MESSAGE = "10000";

	public static final String DEFAULT_MESSAGE_SEND_MAX_RETRIES = "3";

	public static final String DEFAULT_NO_MESSAAGE_BATCHED_PRODUCER = "200";

	public static final String DEFAULT_BUFFER_SIZE = "102400	";

}
