package kh.com.cellcard.common.validators.pipeline;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.repack.com.google.common.reflect.TypeToken;
import kh.com.cellcard.common.exceptions.models.ApplicationError;

public interface ICommandValidator<C extends Command<R>, R> {

    ApplicationError validate(C command);

    default boolean matches(C command) {
        TypeToken<C> typeToken = new TypeToken<>(getClass()) { };
        return typeToken.isSubtypeOf(command.getClass());
    }
}
