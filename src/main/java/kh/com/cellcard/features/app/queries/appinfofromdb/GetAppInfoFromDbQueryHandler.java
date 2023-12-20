package kh.com.cellcard.features.app.queries.appinfofromdb;

import kh.com.cellcard.common.wrapper.CommandHandler;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.repository.StoreProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoFromDbQueryHandler implements CommandHandler<GetAppInfoFromDbQuery, Response> {

    @Autowired
    private StoreProcedureRepository storeProcedureRepo;

    @Override
    public Response handle(GetAppInfoFromDbQuery getAppInfoFromDbQuery) {
        storeProcedureRepo.Execute("",null);
        return Response.success();
    }
}
