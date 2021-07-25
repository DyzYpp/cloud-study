package com.dyz.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class KafkaProducerTest {

    public void sendMessage() throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "175.24.245.45:9092");
        props.put("zookeeper.connect", "175.24.245.45:2181");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            String msg = "这是第" + i + "条消息";
            log.info("正在发送第" + i + "条消息: {}", msg);
            producer.send(new ProducerRecord<>("first", Integer.toString(i), msg));
            Thread.sleep(500);
        }
        producer.close();
    }
}
