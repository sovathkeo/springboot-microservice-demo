package com.jdbcdemo.common.exceptions;

import org.springframework.http.HttpStatusCode;

public interface IApplicationException {

    HttpStatusCode getHttpStatusCode();

    ApplicationError getError();

}
