package kh.com.cellcard.services.ocs.queries;

import jakarta.annotation.PostConstruct;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.common.wrapper.UuidWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.ocs.OcsQueryAccountResponseModel;
import kh.com.cellcard.models.ocs.bundle.OcsQueryBundleResponseModel;
import kh.com.cellcard.models.ocs.payload.OcsPayloadModel;
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
public class OcsQueryService {

    @Autowired
    @Qualifier("applicationLogging")
    ApplicationLogging appLogger;

    final Logger logger = LoggerFactory.getLogger(OcsQueryService.class);
    @Autowired
    WebClientWrapper webClient;

    @Autowired
    ApplicationConfiguration appSetting;

    private String OCS_URL = "";

    @PostConstruct
    private void init() {
        OCS_URL = appSetting.getMicroservices().getOcsService().getBaseUrl();
        this.webClient.setREQUEST_TIME_OUT( appSetting.microservices.ocsService.requestTimeoutMillisecond);
    }

    public Mono<Optional<OcsQueryBundleResponseModel>> queryBundle(String accountId) {
        var payload = OcsPayloadModel.queryBundle(accountId);
        try {
            var response = webClient
                    .postXmlAsync(OCS_URL, payload)
                    .block();
            var bundleRes = new OcsQueryBundleResponseModel(response.getBody());
            return Mono.just(Optional.of(bundleRes));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
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