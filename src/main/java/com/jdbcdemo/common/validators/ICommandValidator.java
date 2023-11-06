package com.jdbcdemo.common.validators;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.repack.com.google.common.reflect.TypeToken;
import com.jdbcdemo.common.exceptions.ApplicationError;
import com.jdbcdemo.common.exceptions.ApplicationException;

public interface ICommandValidator<C extends Command<R>, R> {

    ApplicationError validate(C command);

    default boolean matches(C command) {
        TypeToken<C> typeToken = new TypeToken<>(getClass()) { };
        return typeToken.isSubtypeOf(command.getClass());
    }
}
