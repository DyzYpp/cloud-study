package com.dyz.kafka;

import com.dyz.consumer.KafkaConsumerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaConsumer")
public class KafkaConsumerController {

    @Autowired
    private KafkaConsumerTest kafkaConsumerTest;

    @PostMapping("consumerMessageToKafka")
    public void consumerMessageToKafka() {
//        kafkaConsumerTest.consumerMessage();
    }
}
