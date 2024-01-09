package kh.com.cellcard.features.app.queries.appinfofromdb;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.mediator.RequestCommandHandler;
import kh.com.cellcard.models.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoFromDbCommandHandler extends RequestCommandHandler<GetAppInfoFromDbCommand> {

    @Autowired
    private ApplicationConfiguration appSetting;

    @Override
    public Response handle(GetAppInfoFromDbCommand command) {
        return Response.success(appSetting);
    }
}
