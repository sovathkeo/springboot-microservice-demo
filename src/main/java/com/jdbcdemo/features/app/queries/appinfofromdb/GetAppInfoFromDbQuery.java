package com.jdbcdemo.features.app.queries.appinfofromdb;

import com.jdbcdemo.common.wrapper.CommandWrapper;

public class GetAppInfoFromDbQuery extends CommandWrapper {
    public GetAppInfoFromDbQuery() {
        super("get-app-info-db", null);
    }
}
