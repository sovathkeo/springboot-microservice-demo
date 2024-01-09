package kh.com.cellcard.features.app.queries.appinfofromdb;

import kh.com.cellcard.common.mediator.RequestCommand;

public class GetAppInfoFromDbCommand extends RequestCommand {

    public GetAppInfoFromDbCommand(String accountId, String methodName) {
        this.accountId = accountId;
        this.methodName = methodName;
    }
}
