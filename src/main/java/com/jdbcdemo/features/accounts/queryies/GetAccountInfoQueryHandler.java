package com.jdbcdemo.features.accounts.queryies;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.common.exceptions.ApplicationException;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class GetAccountInfoQueryHandler implements Command.Handler<GetAccountInfoQuery, AResponseBase> {

    @Autowired
    private AccountService accountService;

    @Override
    public AResponseBase handle(GetAccountInfoQuery query) {
        try {
            var acc =  accountService.GetAccountInfo(query.id);
            return acc.get();
        } catch (ApplicationException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
