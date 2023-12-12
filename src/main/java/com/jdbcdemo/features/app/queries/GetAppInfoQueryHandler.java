package com.jdbcdemo.features.app.queries;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.wrapper.CommandHandler;
import com.jdbcdemo.dtos.responses.AppInfoResponseImpl;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoQueryHandler implements CommandHandler<GetAppInfoQuery, Response> {

    @Autowired
    private ApplicationConfiguration appInfo;

    @Autowired
    private CorrelationService correlationService;

    @Override
    public Response handle( GetAppInfoQuery getAppInfoQuery ) {
        AppInfoResponseImpl app = new AppInfoResponseImpl();
        app.setAppSetting(appInfo);
        return Response.success(app, correlationService.getCorrelationId());
    }
}
