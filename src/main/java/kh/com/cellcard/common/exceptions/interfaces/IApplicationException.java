package kh.com.cellcard.common.exceptions.interfaces;

import kh.com.cellcard.common.exceptions.models.ApplicationError;
import org.springframework.http.HttpStatusCode;

public interface IApplicationException {

    HttpStatusCode getHttpStatusCode();

    ApplicationError getError();

}
