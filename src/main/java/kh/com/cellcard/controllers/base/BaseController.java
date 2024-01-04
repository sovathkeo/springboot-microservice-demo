package kh.com.cellcard.controllers.base;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.cellcard.common.enums.smscatalog.MessageCatalogGroup;
import kh.com.cellcard.common.helper.http.HttpRequestHelper;
import kh.com.cellcard.common.mediator.MediatorCommand;
import kh.com.cellcard.common.mediator.MediatorCommandHandler;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.common.helper.RequestParameterHelper;
import kh.com.cellcard.common.helper.logging.ApplicationLog;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.shareservice.ShareServiceImpl;
import kh.com.cellcard.services.smscatalog.SmsCatalogService;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;

public abstract class BaseController {

    protected final ApplicationLog applicationLog = ApplicationLog.getInstance();

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationConfiguration appSetting;
    @Autowired
    private CorrelationService correlationService;
    private final HttpServletRequest request;

    @Autowired
    private ShareServiceImpl shareService;

    @Autowired
    private SmsCatalogService smsCatalogService;

    @Autowired
    private ApplicationContext applicationContext;

    protected BaseController(HttpServletRequest request) {
        super();
        this.request = request;
    }

    public ResponseEntity<Response> execute(MediatorCommand command) {

        initializeLogParams(command, this.request , HttpRequestHelper.getBodyAsString(this.request));
        logger.info(applicationLog.getLogMessage());
        if (!isValidRequestParams(command)) {
            var invalidAccountIdCatalog = smsCatalogService.getResponseMessage(MessageCatalogGroup.invalid_account_id);
            var response = Response.failure(
                    invalidAccountIdCatalog.code,
                    invalidAccountIdCatalog.getEnMessage(),
                    invalidAccountIdCatalog.description,
                    correlationService);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        shareService.setObject(applicationLog);

        var handler = getHandler(command);

        var result = handler.handle(command);

        return  new ResponseEntity<>(result, HttpStatus.OK);

    }

    private void initializeLogParams(
            MediatorCommand request,
            HttpServletRequest httpServletRequest,
            String payload) {

        final String serviceName = appSetting.getApplicationName();
        final String methodName = request.methodName;
        final String channel = request.channel;
        final String requestPlan = request.requestPlan;

        String clientIp = httpServletRequest.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);

        if(clientIp == null) clientIp = httpServletRequest.getRemoteUser();

        applicationLog.initLogParams(
                serviceName,
                methodName,
                requestPlan,
                clientIp,
                channel,
                request.accountId,
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

    protected boolean isValidRequestParams(MediatorCommand command) {
        var validAccountId = RequestParameterHelper.formatAccountId(command.accountId);
        return !validAccountId.equalsIgnoreCase("FALSE") && this.validateRequestParams(command);
    }

    protected boolean validateRequestParams(MediatorCommand command) {
        return true;
    }

    private MediatorCommandHandler getHandler(MediatorCommand command) {
        var beanHandlers = applicationContext.getBeansOfType(MediatorCommandHandler.class);
        return beanHandlers
                .values()
                .stream()
                .filter(bean -> {
                    var g = bean.getClass().getGenericSuperclass();
                    var actualTypes = ((ParameterizedType) g).getActualTypeArguments();
                    var actual = ((Class<?>) actualTypes[0]).getName();
                    return actual.equalsIgnoreCase(command.getClass().getName());
                })
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No Handler Type Matching command"));
    }
}