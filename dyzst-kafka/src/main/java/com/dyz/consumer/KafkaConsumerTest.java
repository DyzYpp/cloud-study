package com.dyz.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerTest {

//    @KafkaListener(topics = {"testTopic"})
//    public void consumerMessage(ConsumerRecord<?, ?> record) {
//        log.info("这是消费的第 {} 条消息：{}", record.key(), record.value());
//    }
}
