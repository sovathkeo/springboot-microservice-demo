package com.jdbcdemo.features.accounts.commands.createaccount;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommandHandler implements Command.Handler<CreateAccountCommand, AResponseBase> {

    @Autowired
    private AccountService accountService;

    @Override
    public AResponseBase handle(CreateAccountCommand command) {
        return accountService.CreateAccount(new Account(command.name, command.age));
    }
}
