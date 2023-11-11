package com.jdbcdemo.features.accounts.commands.createaccount;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.dtos.base.AResponseBase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateAccountCommand implements Command<AResponseBase> {

    @NotEmpty
    public String name;

    @Min(15)
    public int age;
}
