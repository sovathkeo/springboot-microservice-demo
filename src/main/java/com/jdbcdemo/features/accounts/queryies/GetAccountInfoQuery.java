package com.jdbcdemo.features.accounts.queryies;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.dtos.base.AResponseBase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

public class GetAccountInfoQuery implements Command<AResponseBase> {

    public GetAccountInfoQuery() { }

    public GetAccountInfoQuery(long id) {
        this.id = id;
    }

    public long id;

    @Async
    public CompletableFuture<AResponseBase> exec(Pipeline pipeline) {
        var result = execute(pipeline);
        return CompletableFuture.completedFuture(result);
    }

    public void setId(long id) {
        this.id = id;
    }
}
