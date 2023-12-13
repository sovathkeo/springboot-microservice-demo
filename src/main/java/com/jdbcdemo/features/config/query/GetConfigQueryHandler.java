package com.jdbcdemo.features.config.query;

import com.jdbcdemo.common.logging.ApplicationLogging;
import com.jdbcdemo.common.wrapper.CommandHandler;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetConfigQueryHandler extends ApplicationLogging implements CommandHandler<GetConfigQuery, Response> {

    @Autowired
    private CorrelationService correlationService;

    @Override
    public Response handle( GetConfigQuery getConfigQuery ) {

        super.setRequestLogParams("3", "DB", "check-whitelist");
        super.logInfo();

        super.setLastResponseLogParams("4", "success", "0000","successful");
        super.logInfo();

        return Response.success("success", correlationService);
    }
}
