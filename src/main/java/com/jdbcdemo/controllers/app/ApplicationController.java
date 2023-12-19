package com.jdbcdemo.controllers.app;

import com.jdbcdemo.common.wrapper.UuidWrapper;
import com.jdbcdemo.controllers.base.BaseController;
import com.jdbcdemo.features.app.queries.appinfofromdb.GetAppInfoFromDbQuery;
import com.jdbcdemo.features.app.queries.appinfofromgit.GetAppInfoQuery;
import com.jdbcdemo.features.config.query.GetConfigQuery;
import com.jdbcdemo.models.notification.sms.SmsNotificationRequestModel;
import com.jdbcdemo.models.responses.Response;
import com.jdbcdemo.services.caching.CachingService;
import com.jdbcdemo.services.notification.NotificationService;
import com.jdbcdemo.services.ocs.base.OcsServiceImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("applications")
public class ApplicationController extends BaseController {
    @Autowired
    OcsServiceImpl ocsService;
    @Autowired
    CorrelationService correlationService;
    @Autowired
    NotificationService notificationService;

    @Autowired
    CachingService cachingService;

    protected ApplicationController(@Autowired HttpServletRequest request) {
        super(request);
    }


    @GetMapping("/info")
    public Response getAppInfo() {
        cachingService.setString("TEST:test", UuidWrapper.uuidAsString(), 30, TimeUnit.SECONDS);
        var req = new GetAppInfoQuery("get-app-info");
        var cachedValue = cachingService.getString("TEST:test");
        System.out.println("==> cached value = " + cachedValue.get());
        return mediate(req);
    }

    @GetMapping("/info-db")
    public Response getAppInfoFromDb() {
        var req = new GetAppInfoFromDbQuery();
        return mediate(req);
    }

    @GetMapping("test")
    public ResponseEntity<Object> test(@Autowired HttpServletRequest request) {

        super.initializeApplicationLogging(request, "test","85599204681","","CCApp","");

        var result = ocsService
                .query
                .querySubscriberAccount("85599204681")
                .block();

        if (Objects.requireNonNull(result).isEmpty() ) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(Response.success("success", correlationService));
    }

    @GetMapping("get-config")
    public Response testConfig() {
       return mediate(new GetConfigQuery("test-config", ""));
    }


    @GetMapping("push-sms")
    public Response pushSms(@QueryParam("account_id") String account_id, @QueryParam("message") String message){
        notificationService.sms.push(new SmsNotificationRequestModel("Cellcard", account_id,message, "123"));
        return Response.success();
    }

}