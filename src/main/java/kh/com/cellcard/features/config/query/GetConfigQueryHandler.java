package kh.com.cellcard.features.config.query;

import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.common.wrapper.CommandHandler;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetConfigQueryHandler extends ApplicationLogging implements CommandHandler<GetConfigQuery, Response> {

    @Autowired
    private CorrelationService correlationService;

    @Override
    public Response handle(GetConfigQuery getConfigQuery ) {

        super.setRequestLogParams("3", "DB", "check-whitelist");
        super.logInfo();

        super.setLastResponseLogParams("4", "success", "0000","successful");
        super.logInfo();

        return Response.success("success", correlationService);
    }
}
