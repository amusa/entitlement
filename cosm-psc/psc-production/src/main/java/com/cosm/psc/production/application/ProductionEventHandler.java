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
import com.cosm.psc.production.event.kafka.EventConsumer;
import com.cosm.psc.production.event.kafka.KAFKA;
import com.cosm.psc.production.CosmLogger;

import java.util.Properties;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

//@Singleton
//@Startup
@ApplicationScoped
public class ProductionEventHandler {
//TODO:
	private EventConsumer eventConsumer;

	@Resource
	ManagedExecutorService mes;

	@KAFKA
	@Inject
	Properties kafkaProperties;

	@Inject
	Event<CosmEvent> events;

	@Inject
	ProductionService productionService;

        @CosmLogger
	@Inject
	Logger logger;
	
	//private static final Logger logger = Logger.getLogger(ProductionEventHandler.class.getName());

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
