package kh.com.cellcard.common.wrapper;

import an.awesome.pipelinr.Command;
import kh.com.cellcard.common.exceptions.models.ApplicationException;

public abstract class ACommandHandlerWrapper<C extends Command<AResponseBase>, AResponseBase> implements Command.Handler<C, AResponseBase>{

    @Override
    public AResponseBase handle( C c ) {
        try {
            return this.handleWrapper(c);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract AResponseBase handleWrapper( C c) throws ApplicationException;
}
