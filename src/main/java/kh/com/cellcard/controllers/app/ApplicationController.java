package kh.com.cellcard.controllers.app;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import kh.com.cellcard.common.validators.jsonschema.ValidJson;
import kh.com.cellcard.controllers.base.BaseController;
import kh.com.cellcard.features.app.queries.appinfofromdb.GetAppInfoFromDbCommand;
import kh.com.cellcard.features.subscriber.queries.GetSubscriberCommand;
import kh.com.cellcard.models.notification.sms.SmsNotificationRequestModel;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.repository.StoreProcedureRepository;
import kh.com.cellcard.services.hlr.HlrService;
import kh.com.cellcard.services.notification.NotificationService;
import kh.com.cellcard.services.ocs.base.OcsServiceImpl;
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
    CorrelationService correlationService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    StoreProcedureRepository storeProcedureRepo;

    @Autowired
    HlrService hlrService;

    @Autowired
    OcsServiceImpl ocsService;

    protected ApplicationController(@Autowired HttpServletRequest request) {
        super(request);
    }

    @GetMapping("/info")
    public ResponseEntity<Response> getAppInfo(@QueryParam("account_id") String account_id) {
        return super.execute(
                new GetAppInfoFromDbCommand(account_id, "get-subscriber-account"));
    }

    @GetMapping("/subscriber")
    public ResponseEntity<Response> getSubscriberInfo(@QueryParam("account_id") String account_id) {
        return super.execute(
                new GetSubscriberCommand(
                        account_id,
                        "get-subscriber-account",
                        "PREPAID"));
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
        var result = storeProcedureRepo.Execute("", null);
        return Response.success(result.get());
    }

    @GetMapping("test")
    public ResponseEntity<Object> test(
            @Autowired HttpServletRequest request,
            @QueryParam("account_id") String account_id) {

        super.initializeApplicationLogging(request,
                "Subscribe",
                account_id,
                "",
                "CCApp",
                "");
        super.logInfo();

        var result = ocsService.query.querySubscriberAccount(account_id).block();

        return ResponseEntity.ok(Response.success(result.get()));
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