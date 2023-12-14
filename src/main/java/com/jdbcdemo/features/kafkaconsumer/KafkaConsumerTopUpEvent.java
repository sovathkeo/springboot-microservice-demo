package com.jdbcdemo.features.kafkaconsumer;

import com.google.gson.Gson;
import com.jdbcdemo.common.logging.ApplicationLogging;
import com.jdbcdemo.models.kafka.KafkaTopUpModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${spring.kafka.topic-name}", groupId = "${spring.kafka.group-id}")
public class KafkaConsumerTopUpEvent extends ApplicationLogging {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @KafkaHandler
    //@Async
    private void handler1(String message) {
        this.handleAsync(message);
    }

    @Async
    public void handleAsync(String message){
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMessage = String.format("==> consumed", message);
        var topup = new Gson().fromJson(message, KafkaTopUpModel.class);
        //var logMessage = String.format("==> consumed message, accountId = %s", topup.getAccountId());
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info(logMessage);
    }
}