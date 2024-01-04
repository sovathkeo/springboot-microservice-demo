package kh.com.cellcard.features.app.queries.appinfofromdb;

import kh.com.cellcard.common.mediator.MediatorCommand;

public class GetAppInfoFromDbCommand extends MediatorCommand {

    public GetAppInfoFromDbCommand(String accountId, String methodName) {
        this.accountId = accountId;
        this.methodName = methodName;
    }
}
