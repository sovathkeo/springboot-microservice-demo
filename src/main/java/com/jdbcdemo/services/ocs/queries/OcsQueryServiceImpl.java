package com.jdbcdemo.services.ocs.queries;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.logging.ApplicationLogging;
import com.jdbcdemo.common.wrapper.UuidWrapper;
import com.jdbcdemo.common.wrapper.WebClientWrapper;
import com.jdbcdemo.models.ocs.OcsQueryAccountResponseModel;
import com.jdbcdemo.models.ocs.payload.OcsPayloadModel;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Objects;
import java.util.Optional;

@Service
public class OcsQueryServiceImpl  implements OcsQueryService {

    @Autowired
    @Qualifier("applicationLogging")
    ApplicationLogging appLogger;


    final Logger logger = LoggerFactory.getLogger(OcsQueryServiceImpl.class);
    @Autowired
    WebClientWrapper webClient;

    @Autowired
    ApplicationConfiguration appSetting;

    private String OCS_URL = "";

    @PostConstruct
    private void init() {
        OCS_URL = appSetting.getMicroservices().getOcsService().getBaseUrl();
    }

    @Override
    public Mono<Optional<OcsQueryAccountResponseModel>> querySubscriberAccount(String accountId ) {

        appLogger.setRequestLogParams("2","ocs", "query-subscriber");
        appLogger.logInfo();

        var payload = OcsPayloadModel.querySubscriberAccount(UuidWrapper.uuidAsString(), accountId);
        var response = webClient
                .postXmlAsync(OCS_URL, payload)
                .block();
        if (!Objects.requireNonNull(response).hasBody()) {
            throw new RuntimeException("query subscriber account from ocs no response body");
        }
        OcsQueryAccountResponseModel subscriber;
        try {
            subscriber = new OcsQueryAccountResponseModel(response.getBody());
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage(),e);
            return Mono.just(Optional.empty());
        }
        return Mono.just(Optional.of(subscriber));
    }
}