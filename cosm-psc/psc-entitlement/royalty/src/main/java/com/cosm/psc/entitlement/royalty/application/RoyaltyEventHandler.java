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
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyService;
import com.cosm.psc.entitlement.royalty.event.kafka.EventConsumer;
import com.cosm.psc.entitlement.royalty.event.kafka.KAFKA;
import com.cosm.psc.entitlement.royalty.util.CosmLogger;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Singleton
@Startup
public class RoyaltyEventHandler {

	private EventConsumer eventConsumer;

	@Resource
	ManagedExecutorService mes;

        @KAFKA
	@Inject
	Properties kafkaProperties;

	@Inject
	Event<CosmEvent> events;

	@Inject
	RoyaltyService royaltyService;

        @CosmLogger
	@Inject
	Logger logger;

	public void handle(@Observes RoyaltyReady event) {
		logger.info("Handling event " + event);
		royaltyService.when(event);
	}

	@PostConstruct
	private void initConsumer() {
		kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "royalty");
                kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		String royaltiesStage = kafkaProperties.getProperty("royalties.stage.topic"); 

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, royaltiesStage);

		mes.execute(eventConsumer);
	}

}
