package kh.com.cellcard.common.exceptions.models;

import kh.com.cellcard.common.exceptions.interfaces.IApplicationException;
import org.springframework.http.HttpStatusCode;

public class ApplicationRuntimeException  extends RuntimeException implements IApplicationException {
    public ApplicationRuntimeException(Throwable e) {
        super(e);
    }

    @Override
    public HttpStatusCode getHttpStatusCode() {
        return null;
    }

    @Override
    public ApplicationError getError() {
        return null;
    }
}
