package com.eventbroker.royalty.event.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.cosm.common.event.CosmEvent;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class EventConsumer implements Runnable {

//    @CosmLogger
//    @Inject
//    Logger logger;
    private final KafkaConsumer<String, CosmEvent> consumer;
    private final Consumer<CosmEvent> eventConsumer;
    private final AtomicBoolean closed = new AtomicBoolean();
    private final Properties executionProperties;

    public EventConsumer(Properties kafkaProperties, Consumer<CosmEvent> eventConsumer, String... topics) {
        this.eventConsumer = eventConsumer;
        this.executionProperties = kafkaProperties;
        consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(asList(topics));

    }

    @Override
    public void run() {
        try {
            while (!closed.get()) {
                consume();
            }
        } catch (WakeupException e) {
            // will wakeup for closing
        } finally {
            consumer.close();
        }
    }

    private void consume() {
        ConsumerRecords<String, CosmEvent> records = consumer.poll(Long.MAX_VALUE);
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, CosmEvent>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, CosmEvent> record : partitionRecords) {
                eventConsumer.accept(record.value());
            }
            long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
        }

//        ConsumerRecords<String, CosmEvent> records = consumer.poll(Long.MAX_VALUE);
//
//        for (ConsumerRecord<String, CosmEvent> record : records) {
//            eventConsumer.accept(record.value());
//        }
//
//        consumer.commitSync();
    }

    public void stop() {
        closed.set(true);
        consumer.wakeup();
    }

}
