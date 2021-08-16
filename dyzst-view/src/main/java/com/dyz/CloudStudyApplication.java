package com.dyz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CloudStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudStudyApplication.class, args);
        log.info("CloudStudyApplication启动成功, SUCCESS");
    }
}
