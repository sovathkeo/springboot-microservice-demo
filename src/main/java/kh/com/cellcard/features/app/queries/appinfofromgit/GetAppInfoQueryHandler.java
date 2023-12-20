package kh.com.cellcard.features.app.queries.appinfofromgit;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.common.wrapper.CommandHandler;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.shareservice.ShareServiceImpl;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoQueryHandler extends ApplicationLogging implements CommandHandler<GetAppInfoQuery, Response> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationConfiguration appInfo;

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private ShareServiceImpl shareService;

    @Override
    public Response handle(GetAppInfoQuery getAppInfoQuery ) {

        super.setRequestLogParams("100","config-file","load-app-setting-config");

        super.logInfo();

        return Response.success(appInfo, correlationService);
    }
}