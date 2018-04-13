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
import com.eventbroker.royalty.event.kafka.KAFKA;
import com.eventbroker.royalty.util.CosmLogger;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Singleton
@Startup
public class RoyaltyEventBroker {

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
    RoyaltyService broker;

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
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "royalty-broker");
        kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        String productions = kafkaProperties.getProperty("productions.topic");
        String liftings = kafkaProperties.getProperty("liftings.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, liftings, productions);

        mes.submit(eventConsumer);
         
    }
    

    @PreDestroy
    public void close(){
        eventConsumer.stop();
    }
}
