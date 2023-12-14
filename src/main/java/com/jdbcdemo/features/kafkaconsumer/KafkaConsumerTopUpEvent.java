package com.jdbcdemo.features.kafkaconsumer;

import com.jdbcdemo.common.logging.ApplicationLogging;
import com.jdbcdemo.common.wrapper.AsyncOperationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
@KafkaListener(topics = "${spring.kafka.topic-name}", groupId = "${spring.kafka.group-id}")
public class KafkaConsumerTopUpEvent extends ApplicationLogging {

    @Autowired
    private RestTemplate restTemplate;

    private final AsyncOperationWrapper asyncOperationWrapper;

    @Autowired
    public KafkaConsumerTopUpEvent( AsyncOperationWrapper asyncOperationWrapper ) {
        this.asyncOperationWrapper = asyncOperationWrapper;
    }

    @KafkaHandler
    private void handler(String message) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s, completed handler", message);

        //asyncOperationWrapper.executeAsync(this::doAsync);
        //doWork(message);

        //doWorkBlocking(message);

        doWorkReactor(message);

        logger.info(logMsg);
    }


    public void doAsync( ) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s", 123);
        logger.info(logMsg);
    }

    public Mono<Integer> doWorkReactor( Object value) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s, blocking!", value);
        logger.info(logMsg);

        var uri = "https://1923102e-5682-461a-b116-7be17112de16.mock.pstmn.io/delay";
        restTemplate.getForObject(uri, String.class);

        var l = String.format("==> consumed, value = %s, blocking completed", value);
        logger.info(l);
        return Mono.empty();
    }

    public void doWorkBlocking( String value) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s, blocking!", value);
        logger.info(logMsg);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}