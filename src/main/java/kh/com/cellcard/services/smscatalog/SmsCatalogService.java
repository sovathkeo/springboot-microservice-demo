package kh.com.cellcard.services.smscatalog;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.configurations.appsetting.smscatalog.MessageCatalogConfig;
import kh.com.cellcard.common.enums.smscatalog.MessageCatalogGroup;
import kh.com.cellcard.common.helper.logging.ApplicationLog;
import kh.com.cellcard.common.wrapper.SerializationWrapper;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import kh.com.cellcard.models.smscatalog.SmsCatalogResponseModel;
import kh.com.cellcard.services.shareservice.ShareServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class SmsCatalogService {

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

    public MessageCatalogConfig getSmsCatalog(String errorCode, String language) {
        var appLog = shareService.getObject(ApplicationLog.class);
        var serviceName = "HungamaGame";
        var methodName = "Subscribe";
        errorCode = "0000";
        language = "ENGLISH";
        /*if (appLog.isPresent()) {
            var applicationLog = (ApplicationLog)appLog.get();
            serviceName = applicationLog.serviceName;
            methodName = applicationLog.methodName;
        }*/

        var url = BASE_URL
                .replace("{service_name}",serviceName)
                .replace("{method}",methodName)
                .replace("{error_code}",errorCode)
                .replace("{type}","NONE")
                .replace("{language}",language);

        var smsCatalogRes = webClientWrapper
                .getAsync(url)
                .block();

        var sms = SerializationWrapper
                .deserialize(smsCatalogRes.getBody().toString(), SmsCatalogResponseModel.class);
        return null;
    }

}
