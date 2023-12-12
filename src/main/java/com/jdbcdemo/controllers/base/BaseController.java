package com.jdbcdemo.controllers.base;

import an.awesome.pipelinr.Pipeline;
import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.HttpRequestHelper;
import com.jdbcdemo.common.helper.StringHelper;
import com.jdbcdemo.common.helper.logging.LogFormatterHelper;
import com.jdbcdemo.common.wrapper.CommandWrapper;
import com.jdbcdemo.common.wrapper.UuidWrapper;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationConfiguration appSetting;
    @Autowired
    private Pipeline pipeline;
    @Autowired
    private CorrelationService correlationService;
    HttpServletRequest request;
    protected BaseController(HttpServletRequest request) {
        this.request = request;
    }

    public Response mediate(CommandWrapper command ) {
        var logHelper = initializeLogParams(command, this.request , HttpRequestHelper.getBodyAsString(this.request), correlationService.getCorrelationId());
        logger.info(logHelper.getLogMessage());
        var res =  command.execute(pipeline);

        res.setRequestId(correlationService.getRequestId());
        logHelper.action = "response";
        logHelper.result = "success";
        logger.info(logHelper.getLogMessage());
        return res;
    }

    private LogFormatterHelper initializeLogParams(
            CommandWrapper command,
            HttpServletRequest request,
            String payload,
            String correlationId) {

        final String serviceName = appSetting.getApplicationName();
        final String methodName = command.getMethodName();

        String clientIp = request.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);
        String requestId = request.getHeader(HttpHeaderConstant.X_CELLCARD_REQUEST_ID);
        if (StringHelper.isNullOrEmpty(requestId)) {
            requestId = correlationId;
        }
        if (StringHelper.isNullOrEmpty(requestId)) {
            requestId = UuidWrapper.uuidAsString();
        }
        if(clientIp == null) clientIp = request.getRemoteUser();
        // common log declaration
        return new LogFormatterHelper(
                serviceName,
                methodName ,
                correlationId,
                "",
                correlationId,
                requestId,
                clientIp,
                "",
                command.accountId,
                payload,
                "Request");
    }
}