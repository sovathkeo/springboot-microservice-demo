package com.jdbcdemo.controllers.app;

import com.jdbcdemo.controllers.base.BaseController;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.features.app.queries.GetAppInfoQuery;
import com.jdbcdemo.services.ocs.base.OcsServiceImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.servlet.http.HttpServletRequest;
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

    protected ApplicationController(@Autowired HttpServletRequest request) {
        super(request);
    }


    @GetMapping("/info")
    public Response getAppInfo() {
        var req = new GetAppInfoQuery("get-app-info");
        return mediate(req);
    }

    @GetMapping("test")
    public ResponseEntity<Object> test() {
        var result = ocsService
                .query
                .querySubscriberAccount("85599204681")
                .block();

        if (Objects.requireNonNull(result).isEmpty() ) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(Response.success("success", correlationService.getCorrelationId()));
    }

    @GetMapping("test-subscribe")
    public ResponseEntity<Object> testSubscribe() {
        var result = ocsService.subscribe.subscribeBundle("85599204681", "test");
        throw new RuntimeException("test ex");
        //return ResponseEntity.ok(Response.success("", correlationService.getCorrelationId()));
    }
}