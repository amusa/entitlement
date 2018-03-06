package com.eventbroker.taxoil.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;
import com.cosm.common.event.RoyaltyDue;
import com.eventbroker.taxoil.event.kafka.EventConsumer;

import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
@Startup
public class TaxOilEventHandler {

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
	TaxOilBrokerService broker;
	
	
	public void handle(@Observes RoyaltyDue event) {
		logger.info("Handling event " + event);
		broker.when(event);

	}

	public void handle(@Observes CostPosted event) {
		logger.info("Handling event " + event);
		broker.when(event);
	}

	public void handle(@Observes LiftingPosted event) {
		logger.info("Handling event " + event);
		broker.when(event);
	}

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "royalty-broker");
		String royalties = kafkaProperties.getProperty("royalties.topic"); 
		String liftings = kafkaProperties.getProperty("liftings.topic"); 
		String costs = kafkaProperties.getProperty("costs.topic"); 

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, royalties, liftings, costs);

		mes.execute(eventConsumer);
	}

}
