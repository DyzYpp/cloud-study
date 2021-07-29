package com.dyz.kafka;

import com.dyz.producer.KafkaProducerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaSender")
public class KafkaSenderController {

    @Autowired
    private KafkaProducerTest kafkaProducerTest;

    @PostMapping("sendMessageToKafka")
    public void sendMessageToKafka() throws InterruptedException {
        kafkaProducerTest.sendMessage();
    }

}
