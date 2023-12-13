package com.jdbcdemo.features.config.query;

import com.jdbcdemo.common.wrapper.CommandWrapper;
import org.jetbrains.annotations.Nullable;

public class GetConfigQuery extends CommandWrapper {
    public GetConfigQuery( String methodName, @Nullable String accountId ) {
        super(methodName, accountId);
    }
}
