package com.eventbroker.profitoil.application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cosm.common.event.CosmEvent;
import com.cosm.common.event.CostOilDue;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.TaxOilDue;
import com.eventbroker.profitoil.event.kafka.EventConsumer;
import com.eventbroker.profitoil.event.kafka.KAFKA;
import com.eventbroker.profitoil.util.CosmLogger;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Singleton
@Startup
public class ProfitOilEventBroker {

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
    ProfitOilBrokerService broker;

    public void handle(@Observes RoyaltyDue event) {
        logger.info("Handling event " + event);
        broker.when(event);

    }

    public void handle(@Observes CostOilDue event) {
        logger.info("Handling event " + event);
        broker.when(event);

    }

    public void handle(@Observes TaxOilDue event) {
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
        kafkaProperties.put("group.id", "profitoil-broker");
        kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        String royalties = kafkaProperties.getProperty("royalties.topic");
        String liftings = kafkaProperties.getProperty("liftings.topic");
        String costs = kafkaProperties.getProperty("costs.topic");
        String costoil = kafkaProperties.getProperty("costoil.topic");
        String taxoil = kafkaProperties.getProperty("taxoil.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, royalties, liftings, costs, costoil, taxoil);

        mes.execute(eventConsumer);
    }

}
