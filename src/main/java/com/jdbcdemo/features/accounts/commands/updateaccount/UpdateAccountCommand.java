package com.jdbcdemo.features.accounts.commands.updateaccount;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.dtos.base.AResponseBase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateAccountCommand implements Command<AResponseBase> {

    @Min(1)
    @NotNull
    public long id;

    @NotEmpty
    public String name;

    @Min(15)
    @NotNull
    public int age;
}
