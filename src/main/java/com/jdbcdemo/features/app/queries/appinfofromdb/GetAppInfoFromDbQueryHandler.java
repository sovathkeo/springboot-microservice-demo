package com.jdbcdemo.features.app.queries.appinfofromdb;

import com.jdbcdemo.common.wrapper.CommandHandler;
import com.jdbcdemo.models.responses.Response;
import com.jdbcdemo.repository.StoreProcedureRepository;
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
