package com.cosm.account.event.kafka;

import com.cosm.account.CosmLogger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import com.cosm.common.event.CosmEvent;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EventProducer {

    private Producer<String, CosmEvent> producer;
    private String topic;

    //@KAFKA
    //@Inject
//    Properties kafkaProperties;

    @CosmLogger
    @Inject
    Logger logger;

    @PostConstruct
    private void init() {
        Properties kafkaProperties = loadProperties();
        //kafkaProperties.put("transactional.id", UUID.randomUUID().toString());
        producer = new KafkaProducer<>(kafkaProperties);
        topic = kafkaProperties.getProperty("costs.topic");
        //producer.initTransactions();
    }

    public void publish(CosmEvent event) {
        final ProducerRecord<String, CosmEvent> record = new ProducerRecord<>(topic, event);
        try {
            //producer.beginTransaction();
            logger.info("publishing = " + record);
            producer.send(record);
            //producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            //producer.abortTransaction();
        }
    }

    @PreDestroy
    public void close() {
        producer.close();
    }

    private Properties loadProperties() {
        Properties prop;
        try {
            prop = new Properties();
            prop.load(KafkaConfigurator.class.getResourceAsStream("/kafka.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return prop;
    }

}
