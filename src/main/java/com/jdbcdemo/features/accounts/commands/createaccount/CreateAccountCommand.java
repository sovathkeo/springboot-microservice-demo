package com.jdbcdemo.features.accounts.commands.createaccount;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.dtos.base.AResponseBase;

public class CreateAccountCommand implements Command<AResponseBase> {

    public long id;

    public String name;
    public int age;
}
