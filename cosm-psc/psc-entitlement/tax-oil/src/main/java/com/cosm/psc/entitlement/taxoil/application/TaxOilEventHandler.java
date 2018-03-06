package com.cosm.psc.entitlement.taxoil.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.TaxOilReady;
import com.cosm.common.event.kafka.EventConsumer;

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
	TaxOilService taxOilService;

	@Inject
	Logger logger;

	public void handle(@Observes TaxOilReady event) {
		logger.info("Handling event " + event);
		taxOilService.when(event);
	}
	
	
	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "taxoil-handler");
		String taxOilStage = kafkaProperties.getProperty("taxoil.stage.topic"); 

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, taxOilStage);

		mes.execute(eventConsumer);
	}

}
