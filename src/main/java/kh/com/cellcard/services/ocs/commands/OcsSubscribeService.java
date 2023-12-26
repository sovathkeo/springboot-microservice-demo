package kh.com.cellcard.services.ocs.commands;

import jakarta.annotation.PostConstruct;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.ocs.base.OcsResponseBaseModel;
import kh.com.cellcard.models.ocs.bundle.OcsBundleModel;
import kh.com.cellcard.models.ocs.payload.OcsPayloadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Objects;
import java.util.Optional;

@Service
public class OcsSubscribeService {

    private String OCS_URL = "";

    @Autowired
    private ApplicationConfiguration appSetting;

    @Autowired
    private WebClientWrapper webClientWrapper;

    @PostConstruct
    private void init() {
        OCS_URL = appSetting.getMicroservices().getOcsService().getBaseUrl();
    }


    public Mono<Boolean> subscribeBundle( String accountId, String bundleId ) {
        return Mono.just(true);
    }

    public Optional<OcsResponseBaseModel> subscribeBundleNonAuto(String accountId,
                                                                 OcsBundleModel currentBundle,
                                                                 int periodOfSub,
                                                                 String newBundleName,
                                                                 String transactionId) {
        var payload = OcsPayloadModel.subscribeBundleNA(accountId, currentBundle, periodOfSub, newBundleName, transactionId);
        var response = webClientWrapper
                .postXmlAsync(OCS_URL, payload)
                .block();
        if (!Objects.requireNonNull(response).hasBody()) {
            throw new RuntimeException("query subscriber account from ocs no response body");
        }
        try {
            var r = new OcsResponseBaseModel(response.getBody());
            return Optional.of(r);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
