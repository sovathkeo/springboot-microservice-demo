package com.jdbcdemo.features.accounts.commands.updateaccount;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.common.exceptions.ApplicationException;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAccountCommandHandler implements Command.Handler<UpdateAccountCommand, AResponseBase> {

    @Autowired
    private AccountService accountService;

    @Override
    public AResponseBase handle( UpdateAccountCommand updateAccountCommand ) {
        AccountDto acc;
        try {
            acc = accountService.updateAccount(updateAccountCommand);
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
        return acc;
    }
}
