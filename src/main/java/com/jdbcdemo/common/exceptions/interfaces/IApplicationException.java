package com.jdbcdemo.common.exceptions.interfaces;

import com.jdbcdemo.common.exceptions.models.ApplicationError;
import org.springframework.http.HttpStatusCode;

public interface IApplicationException {

    HttpStatusCode getHttpStatusCode();

    ApplicationError getError();

}
