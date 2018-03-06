package com.eventbroker.royalty.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;
import com.eventbroker.royalty.event.kafka.EventConsumer;

import java.util.Properties;
import java.util.logging.Logger;

@Singleton
@Startup
public class RoyaltyEventHandler {

	private EventConsumer eventConsumer;

	@Resource
	ManagedExecutorService mes;

	@Inject
	Properties kafkaProperties;

	@Inject
	Event<CosmEvent> events;

	@Inject
	Logger logger;
	
	@Inject
	RoyaltyBrokerService broker;
	
	
	public void handle(@Observes ProductionPosted event) {
		logger.info("Handling event " + event);
		broker.when(event);

	}

	
	public void handle(@Observes LiftingPosted event) {
		logger.info("Handling event " + event);
		broker.when(event);
	}

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "royalty-borker");
		String productions = kafkaProperties.getProperty("productions.topic"); 
		String liftings = kafkaProperties.getProperty("liftings.topic"); 		

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, productions, liftings);

		mes.execute(eventConsumer);
	}

}
