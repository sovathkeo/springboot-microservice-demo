package com.jdbcdemo.controllers.base;

import an.awesome.pipelinr.Pipeline;
import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.HttpRequestHelper;
import com.jdbcdemo.common.helper.logging.ApplicationLog;
import com.jdbcdemo.common.wrapper.CommandWrapper;
import com.jdbcdemo.models.responses.Response;
import com.jdbcdemo.services.shareservice.ShareServiceImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    private final ApplicationLog applicationLog = new ApplicationLog();

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationConfiguration appSetting;
    @Autowired
    private Pipeline pipeline;
    @Autowired
    private CorrelationService correlationService;
    HttpServletRequest request;

    @Autowired
    private ShareServiceImpl shareService;

    protected BaseController(HttpServletRequest request) {
        super();
        this.request = request;
    }

    public Response mediate(CommandWrapper command ) {

        initializeLogParams(command, this.request , HttpRequestHelper.getBodyAsString(this.request));

        logger.info(applicationLog.getLogMessage());

        shareService.setObject(applicationLog);

        return  command.execute(pipeline);
    }

    private void initializeLogParams(
            CommandWrapper command,
            HttpServletRequest request,
            String payload) {

        final String serviceName = appSetting.getApplicationName();
        final String methodName = command.getMethodName();

        String clientIp = request.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);

        if(clientIp == null) clientIp = request.getRemoteUser();

        applicationLog.initLogParams(
            serviceName,
            methodName,
            "",
            clientIp,
            "",
            command.accountId,
            payload,
            correlationService );
    }

    protected void initializeApplicationLogging(
        HttpServletRequest request,
        String methodName,
        String accountId,
        String requestPlan,
        String channel,
        String payload) {

        final String serviceName = appSetting.getApplicationName();

        String clientIp = request.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);

        if(clientIp == null) clientIp = request.getRemoteUser();

        applicationLog.initLogParams(
            serviceName,
            methodName,
            requestPlan,
            clientIp,
            channel,
            accountId,
            payload,
            correlationService );

        shareService.setObject(applicationLog);
    }
}