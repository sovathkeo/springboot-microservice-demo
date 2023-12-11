package com.jdbcdemo.controllers.app;

import com.jdbcdemo.controllers.base.BaseController;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.dtos.responses.ResponseBase;
import com.jdbcdemo.features.app.queries.GetAppInfoQuery;
import com.jdbcdemo.services.ocs.base.OcsServiceImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("applications")
public class ApplicationController extends BaseController {

    @Autowired
    OcsServiceImpl ocsService;

    @Autowired
    CorrelationService correlationService;

    @GetMapping("/info")
    public ResponseBase<AResponseBase> getAppInfo() {
        return mediate(new GetAppInfoQuery());
    }

    @GetMapping("test")
    public ResponseEntity<Object> test() {
        var result = ocsService.query.querySubscriberAccount("85599204681");
        return ResponseEntity.ok(Response.success(result.block(), correlationService.getCorrelationId()));
    }

    @GetMapping("test-subscribe")
    public ResponseEntity<Object> testSubscribe() {
        var result = ocsService.subscribe.subscribeBundle("85599204681", "test");
        throw new RuntimeException("test ex");
        //return ResponseEntity.ok(Response.success("", correlationService.getCorrelationId()));
    }
}