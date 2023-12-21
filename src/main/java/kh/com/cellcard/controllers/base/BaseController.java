package kh.com.cellcard.controllers.base;

import an.awesome.pipelinr.Pipeline;
import jakarta.servlet.http.HttpServletRequest;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.common.helper.HttpRequestHelper;
import kh.com.cellcard.common.helper.logging.ApplicationLog;
import kh.com.cellcard.common.wrapper.CommandWrapper;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.shareservice.ShareServiceImpl;
import kh.com.cellcard.services.tracing.CorrelationService;
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
            "CCApp",
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

    protected void logInfo(){
        logger.info(applicationLog.getLogMessage());
    }
}