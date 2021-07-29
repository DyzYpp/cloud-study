package com.dyz.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            String msg = "这是第" + i + "条消息";
            log.info("正在发送第" + i + "条消息: {}", msg);
            kafkaTemplate.send(new ProducerRecord<>("testTopic", Integer.toString(i), msg));
            Thread.sleep(500);
        }
    }
}
