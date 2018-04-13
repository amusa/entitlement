package com.cosm.shipping.lifting.event.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import com.cosm.common.event.CosmEvent;
import com.cosm.shipping.lifting.CosmLogger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.ProducerConfig;

@ApplicationScoped
public class EventProducer {

    private Producer<String, CosmEvent> producer;
    private String topic;

    @KAFKA
    @Inject
    Properties kafkaProperties;

    @CosmLogger
    @Inject
    Logger logger;

    @PostConstruct
    private void init() {
        //kafkaProperties.put("transactional.id", UUID.randomUUID().toString());
        kafkaProperties.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        producer = new KafkaProducer<>(kafkaProperties);
        topic = kafkaProperties.getProperty("liftings.topic");
        //producer.initTransactions();
    }

    public void publish(CosmEvent event) {
        final ProducerRecord<String, CosmEvent> record = new ProducerRecord<>(topic, event);
        try {
            //producer.beginTransaction();
            logger.info("publishing = " + record);
           // producer.send(record);
           producer.send(record, (metadata, exception) -> {
                if (metadata != null) {
                    logger.info(String.format("sent record(key=%s value=%s) meta(partition=%d, offset=%d)\n",
                            record.key(), record.value(), metadata.partition(),
                            metadata.offset()));
                } else {
                    exception.printStackTrace();
                }

            });
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
}
