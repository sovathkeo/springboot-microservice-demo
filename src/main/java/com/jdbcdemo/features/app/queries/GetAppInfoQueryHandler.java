package com.jdbcdemo.features.app.queries;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.helper.logging.ApplicationLog;
import com.jdbcdemo.common.wrapper.CommandHandler;
import com.jdbcdemo.dtos.responses.AppInfoResponseImpl;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.shareservice.ShareServiceImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class GetAppInfoQueryHandler implements CommandHandler<GetAppInfoQuery, Response> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationConfiguration appInfo;

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private ShareServiceImpl shareService;

    @Override
    public Response handle( GetAppInfoQuery getAppInfoQuery ) {

        AppInfoResponseImpl app = new AppInfoResponseImpl();

        ApplicationLog appLog = (ApplicationLog) shareService.getObject(ApplicationLog.class);

        appLog .setRequestLogParams( "1","config-file","load-app-setting-config");

        logger.info(appLog.getLogMessage());

        app.setAppSetting(appInfo);

        return Response.success(app, correlationService);
    }
}
