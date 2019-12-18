package org.apache.synapse.protocol.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.inbound.PollingProcessor;
import org.apache.synapse.startup.quartz.StartUpController;
import org.apache.synapse.task.Task;
import org.apache.synapse.task.TaskDescription;
import org.apache.synapse.task.TaskStartupObserver;

import java.util.Properties;

public class KAFKAProcessor implements PollingProcessor, TaskStartupObserver {
	private static final Log log = LogFactory.getLog(KAFKAProcessor.class
			.getName());

	private KAFKAPollingConsumer pollingConsumer;
	private String name;
	private Properties kafkaProperties;
	private long interval;
	private String injectingSeq;
	private String onErrorSeq;
	private SynapseEnvironment synapseEnvironment;
	private StartUpController startUpController;
	private int threadCount;

	public KAFKAProcessor(String name, Properties kafkaProperties,
			long scanInterval, String injectingSeq, String onErrorSeq,
			SynapseEnvironment synapseEnvironment) {

		this.name = name;
		this.kafkaProperties = kafkaProperties;
		this.interval = scanInterval;
		this.injectingSeq = injectingSeq;
		this.onErrorSeq = onErrorSeq;
		this.synapseEnvironment = synapseEnvironment;
		this.threadCount = threadCount;
	}

	public void init() {
		log.info("Initializing inbound KAFKA listener for destination " + name);
		try {
			pollingConsumer = new KAFKAPollingConsumer(kafkaProperties);
		} catch (Exception e) {
			log.error(e.getMessage());
			return;
		}
		pollingConsumer.registerHandler(new KAFKAInjectHandler(injectingSeq,
				onErrorSeq, synapseEnvironment, kafkaProperties
						.getProperty(KAFKAConstants.CONTENT_TYPE)));
		try {
			pollingConsumer.startsMessageListener();
		} catch (Exception e) {
			log.error("Error initializing message listener");
		}
		start();

	}

	public void start() {
		log.info("Inbound KAFKA listener Started for destination " + name);
		try {
			Task task = new KAFKATask(pollingConsumer);
			TaskDescription taskDescription = new TaskDescription();
			taskDescription.setName(name + "-KAFKA-EP");
			taskDescription.setTaskGroup("KAFKA-EP");
			taskDescription.setInterval(interval);
			taskDescription.setIntervalInMs(true);
			taskDescription.addResource(TaskDescription.INSTANCE, task);
			taskDescription.addResource(TaskDescription.CLASSNAME, task
					.getClass().getName());
			startUpController = new StartUpController();
			startUpController.setTaskDescription(taskDescription);
			startUpController.init(synapseEnvironment);

		} catch (Exception e) {
			log.error("Could not start Kafka Processor. Error starting up scheduler. Error: "
					+ e.getLocalizedMessage());
		}
	}

	public void destroy() {
		log.info("Inbound Kafka listener ending operation on destination "
				+ name);
		startUpController.destroy();
		try {
			pollingConsumer.destroy();
		} catch (Exception e) {
			log.error("Could not destroy kafka processor :  "
					+ e.getLocalizedMessage());
		}
	}

	public void update() {
		start();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
