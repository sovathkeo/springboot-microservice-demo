package kh.com.cellcard.services.billing;

import jakarta.annotation.PostConstruct;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.wrapper.SerializationWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.billing.payload.BillingPayloadModel;
import kh.com.cellcard.models.billing.response.BillingResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingService {

    private String BASE_URL = "EMPTY";
    @Autowired
    private WebClientWrapper webClientWrapper;

    @Autowired
    private ApplicationConfiguration appSetting;

    @PostConstruct
    private void init() {
        if (appSetting.microservices == null || appSetting.microservices.billingService == null) {
            return;
        }
        BASE_URL = appSetting.microservices.billingService.baseUrl;
    }

    public Optional<BillingResponseModel> fetchProfile(String accountId) {
        var payload = BillingPayloadModel.crmFetchProfile(accountId);
        var res = webClientWrapper.post(BASE_URL, payload);
        var billingProfile = SerializationWrapper.deserialize(res.getBody(), BillingResponseModel.class);
        return Optional.of(billingProfile);
    }
}