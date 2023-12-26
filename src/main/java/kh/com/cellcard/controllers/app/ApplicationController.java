package kh.com.cellcard.controllers.app;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.enums.smscatalog.MessageCatalogGroup;
import kh.com.cellcard.common.validators.jsonschema.ValidJson;
import kh.com.cellcard.controllers.base.BaseController;
import kh.com.cellcard.features.app.queries.appinfofromgit.GetAppInfoQuery;
import kh.com.cellcard.features.config.query.GetConfigQuery;
import kh.com.cellcard.models.notification.sms.SmsNotificationRequestModel;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.repository.StoreProcedureRepository;
import kh.com.cellcard.services.billing.BillingService;
import kh.com.cellcard.services.notification.NotificationService;
import kh.com.cellcard.services.ocs.base.OcsServiceImpl;
import kh.com.cellcard.services.smscatalog.SmsCatalogService;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kh.com.cellcard.common.validators.jsonschema.SchemaLocations.PUSHSMS_SCHEMA;

@RestController
@RequestMapping("applications")
public class ApplicationController extends BaseController {

    @Autowired
    OcsServiceImpl ocsService;

    @Autowired
    BillingService billingService;

    @Autowired
    CorrelationService correlationService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    StoreProcedureRepository storeProcedureRepo;

    @Autowired
    SmsCatalogService smsCatalogService;

    @Autowired
    ApplicationConfiguration appSetting;

    protected ApplicationController(@Autowired HttpServletRequest request) {
        super(request);
    }


    @GetMapping("/info")
    public Response getAppInfo(@QueryParam("account_id") String account_id) {
        var req = new GetAppInfoQuery("get-app-info", account_id);
        return mediate(req);
    }

    @GetMapping("/info-db")
    public Response getAppInfoFromDb(HttpServletRequest request, @QueryParam("account_id") String account_id) {
        super.initializeApplicationLogging(request,
                "getAppInfoFromDb",
                account_id,
                "",
                "Testing",
                "");
        super.logInfo();
        //var req = new GetAppInfoFromDbQuery();
        var result = storeProcedureRepo.Execute("", null);
        return Response.success(result.get(), correlationService);
    }

    @GetMapping("test")
    public ResponseEntity<Object> test(
            @Autowired HttpServletRequest request,
            @QueryParam("account_id") String account_id) {

        super.initializeApplicationLogging(request,
                "unsubscribe",
                account_id,
                "",
                "CCApp",
                "");
        super.logInfo();

        /*var result = ocsService
                .query
                .queryBundle(account_id)
                .block();*/

        /*var targetBundleId = "SereyP_$1";
        var nonAutoBundle = targetBundleId + "NA";
        var bundle = result
                .get()
                .bundles
                .stream()
                .filter(b -> b.bundleId.equalsIgnoreCase(targetBundleId))
                .findFirst()
                .get();
        var subscribeNAResult = ocsService
                .subscribe
                .subscribeBundleNonAuto(account_id, bundle, 7, nonAutoBundle, "test_subscribe");*/
        /*var sms = smsCatalogService
                .getResponseMessage(MessageCatalogGroup.subscribe_success);*/

        var smsCatalog = smsCatalogService.getSmsCatalog("","");

        return ResponseEntity.ok(Response.success(
                smsCatalog,
                correlationService));
    }

    @GetMapping("get-config")
    public Response testConfig() {
       return mediate(new GetConfigQuery("test-config", ""));
    }


    @PostMapping("push-sms")
    public Response pushSms(@QueryParam("account_id") @Valid String account_id,
                            @QueryParam("message") String message,
                            @ValidJson(PUSHSMS_SCHEMA) PushSms request){
        notificationService.sms.push(new SmsNotificationRequestModel(request.originator, request.accountId,request.message, "123"));
        return Response.success();
    }



    public static class PushSms {
        public String accountId;
        public String originator;
        public String message;
    }
}