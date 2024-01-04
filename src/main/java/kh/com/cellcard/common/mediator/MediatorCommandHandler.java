package kh.com.cellcard.common.mediator;

import kh.com.cellcard.models.responses.Response;

public abstract class MediatorCommandHandler<TCommand extends MediatorCommand> {
    public abstract Response handle(TCommand command);
}
