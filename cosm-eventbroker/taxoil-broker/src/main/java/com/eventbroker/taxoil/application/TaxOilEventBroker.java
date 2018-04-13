package com.eventbroker.taxoil.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;


import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.psc.entitlement.royalty.util.CosmLogger;
import com.eventbroker.taxoil.event.kafka.EventConsumer;
import com.eventbroker.taxoil.event.kafka.KAFKA;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Singleton
@Startup
public class TaxOilEventBroker {

	private EventConsumer eventConsumer;

	@Resource
	ManagedExecutorService mes;

        @KAFKA
	@Inject
	Properties kafkaProperties;

	@Inject
	Event<CosmEvent> events;

        @CosmLogger
	@Inject
	Logger logger;
	
	@Inject
	TaxOilService broker;
	
	
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
                kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "taxoil-broker");
                kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
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
