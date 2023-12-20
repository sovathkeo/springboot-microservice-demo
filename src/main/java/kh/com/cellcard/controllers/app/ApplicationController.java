package kh.com.cellcard.controllers.app;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.QueryParam;
import kh.com.cellcard.controllers.base.BaseController;
import kh.com.cellcard.features.app.queries.appinfofromdb.GetAppInfoFromDbQuery;
import kh.com.cellcard.features.app.queries.appinfofromgit.GetAppInfoQuery;
import kh.com.cellcard.features.config.query.GetConfigQuery;
import kh.com.cellcard.models.notification.sms.SmsNotificationRequestModel;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.notification.NotificationService;
import kh.com.cellcard.services.ocs.base.OcsServiceImpl;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("applications")
public class ApplicationController extends BaseController {

    @Autowired
    OcsServiceImpl ocsService;

    @Autowired
    CorrelationService correlationService;

    @Autowired
    NotificationService notificationService;

    protected ApplicationController(@Autowired HttpServletRequest request) {
        super(request);
    }


    @GetMapping("/info")
    public Response getAppInfo() {
        var req = new GetAppInfoQuery("get-app-info");
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