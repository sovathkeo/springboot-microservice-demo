package kh.com.cellcard.features.app.queries.appinfofromdb;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.mediator.MediatorCommandHandler;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoFromDbHandler extends MediatorCommandHandler<GetAppInfoFromDbCommand> {

    @Autowired
    private ApplicationConfiguration appSetting;

    @Autowired
    private CorrelationService correlationService;

    @Override
    public Response handle(GetAppInfoFromDbCommand command) {
        return Response.success("success", appSetting, correlationService);
    }
}
