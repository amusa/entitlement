package com.cosm.psc.entitlement.royalty.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.RoyaltyReady;
import com.cosm.common.event.kafka.EventConsumer;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyService;

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
	RoyaltyService royaltyService;

	@Inject
	Logger logger;

	public void handle(@Observes RoyaltyReady event) {
		logger.info("Handling event " + event);
		royaltyService.when(event);
	}

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put("group.id", "royalty-handler");
		String royaltiesStage = kafkaProperties.getProperty("royalties.stage.topic"); 

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, royaltiesStage);

		mes.execute(eventConsumer);
	}

}
