package com.cosm.psc.production.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.kafka.EventConsumer;

import java.util.Properties;
import java.util.logging.Logger;

@Singleton
@Startup
public class ProductionEventHandler {

	private EventConsumer eventConsumer;

	@Resource
	ManagedExecutorService mes;

	@Inject
	Properties kafkaProperties;

	@Inject
	Event<CosmEvent> events;

	@Inject
	ProductionService productionService;

	@Inject
	Logger logger;

	public void handle(@Observes CosmEvent event) {
		logger.info("Handling event " + event);
	}

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "production-handler");
		String orders = kafkaProperties.getProperty("productions.topic"); //TODO: remove. testing only

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, orders);

		mes.execute(eventConsumer);
	}

}
