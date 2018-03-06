package com.cosm.psc.entitlement.costoil.application;

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
import com.cosm.psc.entitlement.costoil.event.kafka.EventConsumer;

import java.util.Properties;
import java.util.logging.Logger;

@Singleton
@Startup
public class CostOilEventHandler {

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
	CostOilService broker;
	
	
	public void handle(@Observes CostOilReady event) {
		logger.info("Handling event " + event);
		broker.when(event);

	}

	

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "costoil-handler");
		String costOilStage = kafkaProperties.getProperty("costoil.stage.topic"); 
		
		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, costOilStage);

		mes.execute(eventConsumer);
	}

}
