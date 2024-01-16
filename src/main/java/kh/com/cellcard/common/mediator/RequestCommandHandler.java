package kh.com.cellcard.common.mediator;

import kh.com.cellcard.common.logging.ApplicationLogging;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RequestCommandHandler<TCommand extends RequestCommand> extends ApplicationLogging {

    @Autowired
    private CorrelationService correlationService;

    public abstract Response handle(TCommand command);

    public Response mediate(TCommand command) {
        var res = this.handle(command);
        res.setMeta(correlationService);
        return res;
    }
}
