package kh.com.cellcard.features.kafkaconsumer;

import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.common.wrapper.AsyncOperationWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import reactor.core.publisher.Mono;

//@Component
//@KafkaListener(
//        topics = "${spring.kafka.topic-name:KafkaConsumerTopUpEvent}",
//        groupId = "${spring.kafka.group-id: :KafkaConsumerTopUpEvent}",
//        autoStartup = "${spring.kafka.required:false}")
public class KafkaConsumerTopUpEvent extends ApplicationLogging {
    private final WebClientWrapper webClient;
    private final AsyncOperationWrapper asyncOperationWrapper;

    @Autowired
    public KafkaConsumerTopUpEvent(AsyncOperationWrapper asyncOperationWrapper, WebClientWrapper webClient ) {
        this.asyncOperationWrapper = asyncOperationWrapper;
        this.webClient = webClient;
    }

    @KafkaHandler
    private void handler(String message) {
        asyncOperationWrapper.executeAsync(message, this::doAsync);
    }


    public void doAsync(Object value) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s", value);
        logger.info(logMsg);
    }

    public Mono<Integer> doWorkReactor( Object value) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> consumed, value = %s, blocking!", value);
        logger.info(logMsg);

        var uri = "https://622bf548-c8f6-409c-a314-eb4a23a3caf9.mock.pstmn.io/delay-5s-has-body";

        webClient
                .getAsync(uri)
                .subscribe(res -> {

                    var l = String.format("==> consumed, value = %s, completed API call", value);
                    var b = res.getBody();

                    logger.info(l);
                });

        var l = String.format("==> consumed, value = %s, blocking completed", value);
        logger.info(l);
        return Mono.empty();
    }
}