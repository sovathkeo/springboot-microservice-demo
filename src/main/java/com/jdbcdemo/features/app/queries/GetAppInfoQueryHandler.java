package com.jdbcdemo.features.app.queries;

import com.jdbcdemo.common.configurations.ApplicationConfiguration;
import com.jdbcdemo.common.wrapper.CommandHandler;
import com.jdbcdemo.dtos.base.AResponseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAppInfoQueryHandler implements CommandHandler<GetAppInfoQuery, AResponseBase> {

    @Autowired
    private ApplicationConfiguration appInfo;

    @Override
    public AResponseBase handle( GetAppInfoQuery getAppInfoQuery ) {
        return appInfo;
    }
}
