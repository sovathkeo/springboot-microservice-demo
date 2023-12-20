package kh.com.cellcard.features.config.query;

import kh.com.cellcard.common.wrapper.CommandWrapper;
import org.jetbrains.annotations.Nullable;

public class GetConfigQuery extends CommandWrapper {
    public GetConfigQuery( String methodName, @Nullable String accountId ) {
        super(methodName, accountId);
    }
}
