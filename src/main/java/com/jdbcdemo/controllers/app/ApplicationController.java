package com.jdbcdemo.controllers.app;

import com.jdbcdemo.controllers.base.BaseController;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.dtos.responses.ResponseImpl;
import com.jdbcdemo.features.app.queries.GetAppInfoQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("applications")
public class ApplicationController extends BaseController {

    @GetMapping("/info")
    public ResponseImpl<AResponseBase> getAppInfo() {
        return mediate(new GetAppInfoQuery());
    }
}
