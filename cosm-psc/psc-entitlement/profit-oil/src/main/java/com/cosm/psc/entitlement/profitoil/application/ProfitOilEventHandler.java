package com.cosm.psc.entitlement.profitoil.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.CostOilReady;
import com.cosm.common.event.ProfitOilReady;
import com.cosm.psc.entitlement.profitoil.event.kafka.EventConsumer;

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
	ProfitOilService broker;
	
	
	public void handle(@Observes ProfitOilReady event) {
		logger.info("Handling event " + event);
		broker.when(event);

	}

	

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "profitoil-handler");
		String profitOilStage = kafkaProperties.getProperty("profitoil.stage.topic"); 
		
		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, profitOilStage);

		mes.execute(eventConsumer);
	}

}
