package kh.com.cellcard.services.hlr;

import jakarta.annotation.PostConstruct;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.common.wrapper.DateTimeWrapper;
import kh.com.cellcard.common.wrapper.UuidWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.hlr.base.HlrResponseBaseModel;
import kh.com.cellcard.models.hlr.payload.HlrPayloadModel;
import kh.com.cellcard.services.ocs.queries.OcsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HlrService {

    final Logger logger = LoggerFactory.getLogger(HlrService.class);
    @Autowired
    WebClientWrapper webClient;

    @Autowired
    ApplicationConfiguration appSetting;

    private String FULL_URL = "";

    private final String TRANSACTION_TIME_FORMAT = "yyyyMMddHHmmssSSS";

    @PostConstruct
    private void init() {
        FULL_URL = appSetting.getMicroservices().getHlrService().baseUrl;
        this.webClient.setREQUEST_TIME_OUT(appSetting.microservices.hlrService.requestTimeoutMillisecond);
    }

    public Optional<HlrResponseBaseModel> querySubscriber(String accountId, String transactionId) {
        if (StringHelper.isNullOrEmpty(transactionId)) {
            transactionId = UuidWrapper.uuidAsString();
        }
        var transactionTime = DateTimeWrapper.now(TRANSACTION_TIME_FORMAT);
        var payload = HlrPayloadModel.querySubscriberByMSISDN(transactionId, transactionTime, accountId);
        var res = webClient
                .postXmlAsync(FULL_URL, payload)
                .block();
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of(new HlrResponseBaseModel(res.getBody()));
    }
}
