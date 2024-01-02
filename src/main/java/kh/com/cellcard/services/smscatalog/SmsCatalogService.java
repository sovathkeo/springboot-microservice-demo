package kh.com.cellcard.services.smscatalog;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.configurations.appsetting.smscatalog.MessageCatalogConfig;
import kh.com.cellcard.common.enums.smscatalog.MessageCatalogGroup;
import kh.com.cellcard.common.helper.logging.ApplicationLog;
import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.common.wrapper.SerializationWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.smscatalog.SmsCatalogResponseModel;
import kh.com.cellcard.services.shareservice.ShareServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Service
public class SmsCatalogService extends ApplicationLogging {

    private final HashMap<String, MessageCatalogConfig>[] smsCatalogs;
    private final WebClientWrapper webClientWrapper;
    private final ShareServiceImpl shareService;

    private final String BASE_URL;

    @Autowired
    public SmsCatalogService(ApplicationConfiguration appSetting, WebClientWrapper webClientWrapper, ShareServiceImpl shareService) {
        this.smsCatalogs = appSetting.smsCatalogs;
        this.webClientWrapper = webClientWrapper;
        this.webClientWrapper.setREQUEST_TIME_OUT(appSetting.microservices.smsCatalogService.requestTimeoutMillisecond);
        this.shareService = shareService;
        BASE_URL = appSetting.microservices.smsCatalogService.baseUrl;
    }

    public MessageCatalogConfig getResponseMessage(MessageCatalogGroup group) {

        var catalogGroup = Arrays
            .stream(smsCatalogs)
            .filter(g -> g.containsKey(group.getKey()))
            .findFirst();

        return catalogGroup
                .map(catalog -> catalog.get(group.getKey()))
                .orElse(null);

    }

    public Optional<SmsCatalogResponseModel> getSmsCatalog(String errorCode, String language) {

        /*final Logger logger = LoggerFactory.getLogger(this.getClass());
        var logMsg = String.format("==> getSmsCatalog, errorCode = %s", errorCode);
        logger.info(logMsg);*/

        webClientWrapper
            .getAsync("https://622bf548-c8f6-409c-a314-eb4a23a3caf9.mock.pstmn.io/delay-5s-has-body")
            .block();

        super.setRequestLogParams("-1","SMS-Catalog","get-sms-catalog");
        super.logInfo();

        var url = buildSmsCatalogUrl(errorCode, language);

        var smsCatalogRes = webClientWrapper
                .getAsync(url)
                .block();
        if (smsCatalogRes == null) {
            return Optional.empty();
        }
        
        var sms = SerializationWrapper
                .deserialize(smsCatalogRes.getBody(), SmsCatalogResponseModel.class);
        return Optional.of(sms);
    }

    private String buildSmsCatalogUrl(String errorCode, String language) {
        var serviceName = "";
        var methodName = "";
        var appLog = shareService.getObject(ApplicationLog.class);
        if (appLog.isPresent()) {
            var applicationLog = (ApplicationLog)appLog.get();
            serviceName = applicationLog.serviceName;
            methodName = applicationLog.methodName;
        }
        return BASE_URL
                .replace("{service_name}",serviceName)
                .replace("{method}",methodName)
                .replace("{error_code}",errorCode)
                .replace("{type}","NONE")
                .replace("{language}",language);
    }
}
