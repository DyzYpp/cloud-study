package com.dyz.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

@Component
@Slf4j
public class KafkaConsumerTest {

    public void consumerMessage() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "175.24.245.45:9092");
        props.put("zookeeper.connect", "175.24.245.45:2181");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("first"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                log.info("这是消费的第 {} 条消息：{}", record.key(), record.value());
        }
    }
}
