package com.eventbroker.profitoil.application;

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
import com.eventbroker.profitoil.event.kafka.EventConsumer;

import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
@Startup
public class ProfitOilEventHandler {

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
	ProfitOilBrokerService broker;
	
	
	public void handle(@Observes ProductionPosted event) {
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
		kafkaProperties.put("group.id", "royalty-borker");
		String royalties = kafkaProperties.getProperty("royalty.topic"); 
		String liftings = kafkaProperties.getProperty("lifting.topic"); 
		String costs = kafkaProperties.getProperty("cost.topic"); 
		String costoil = kafkaProperties.getProperty("costoil.topic"); 
		String taxoil = kafkaProperties.getProperty("taxoil.topic"); 

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, royalties, liftings, costs, costoil, taxoil);

		mes.execute(eventConsumer);
	}

}
