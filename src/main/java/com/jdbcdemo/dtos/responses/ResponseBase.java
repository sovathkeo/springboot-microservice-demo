package com.jdbcdemo.dtos.responses;

import com.jdbcdemo.common.exceptions.models.ApplicationError;
import com.jdbcdemo.dtos.base.AResponseBase;

public class ResponseBase<TData extends AResponseBase> {

    private final int statusCode;
    private final String message;
    private final ApplicationError error;
    private final String correlationId;
    private final TData data;

    protected ResponseBase( int statusCode, String message, TData data, ApplicationError error, String correlationId) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.error = error;
        this.correlationId = correlationId;
    }

    public static ResponseBase<AResponseBase> success( AResponseBase data, String correlationId) {
        return new ResponseBase<>(200,"success",data, null, correlationId);
    }

    public static ResponseBase<AResponseBase> success( String data, String correlationId) {
        return new ResponseBase<>(200,"success",AResponseBase.success(data), null, correlationId);
    }

    public static ResponseBase<AResponseBase> success( boolean bool, String correlationId) {
        return new ResponseBase<>(200,"success",AResponseBase.success(bool), null, correlationId);
    }

    public static ResponseBase<AResponseBase> Failed(
        int statusCode,
        String message,
        ApplicationError error,
        String correlationId) {

        return new ResponseBase<>(statusCode,message,null, error, correlationId);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public TData getData() {
        return data;
    }

    public ApplicationError getError() {
        return error;
    }

    public String getCorrelationId() { return correlationId; }
}

