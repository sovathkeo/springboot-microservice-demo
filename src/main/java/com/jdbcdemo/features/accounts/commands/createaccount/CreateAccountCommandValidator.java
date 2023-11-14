package com.jdbcdemo.features.accounts.commands.createaccount;

import com.jdbcdemo.common.exceptions.models.ApplicationError;
import com.jdbcdemo.common.validators.ICommandValidator;
import com.jdbcdemo.dtos.base.AResponseBase;


public class CreateAccountCommandValidator implements ICommandValidator<CreateAccountCommand, AResponseBase> {

    @Override
    public ApplicationError validate(CreateAccountCommand command) {
        if (command.name.isEmpty() || command.name.isBlank()) {
            return new ApplicationError("400_01","filed id must greater than 0");
        }

        return ApplicationError.applicationNone();
    }
}
