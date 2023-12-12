package com.jdbcdemo.common.wrapper;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.dtos.responses.Response;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommandWrapper implements Command<Response> {
    public String methodName;
    public String accountId;
    public CommandWrapper(String methodName, @Nullable String accountId) {
        this.methodName = methodName;
        this.accountId = accountId;
    }
}
