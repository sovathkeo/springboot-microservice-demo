package com.jdbcdemo.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jdbcdemo.common.wrapper.DateTimeWrapper;
import com.jdbcdemo.common.wrapper.UuidWrapper;


public class Response {

    @JsonProperty("meta")
    ResponseMeta meta;

    @JsonProperty("data")
    ResponseData data;

    protected Response() {
        this.meta = ResponseMeta.buildMeta();
        this.data = ResponseData.success();
    }

    protected Response(String errorMessage) {
        this.meta = ResponseMeta.buildMeta();
        this.data = ResponseData.success(errorMessage);
    }

    protected Response(String errorMessage, String correlationId) {
        this.meta = ResponseMeta.buildMeta(correlationId);
        this.data = ResponseData.success(errorMessage);
    }


    protected Response(String errorCode, String errorMessage, String errorDescription, String correlationId) {
        this.meta = ResponseMeta.buildMeta(correlationId);
        this.data = ResponseData.failed(errorCode, errorMessage, errorDescription);
    }

    public static Response success() {
        return new Response();
    }

    public static Response success(String errorMessage) {
        return new Response(errorMessage);
    }

    public static Response success(String errorMessage, String correlationId) {
        return new Response(errorMessage, correlationId);
    }

    // Start build failed response

    public static Response failure(String errorCode, String errorMessage, String errorDescription, String correlationId) {
        return new Response(errorCode, errorMessage, errorDescription, correlationId);
    }

    // End build failed response
}


class ResponseData {
    @JsonProperty("error_code")
    String errorCode;
    @JsonProperty("error_message")
    String errorMessage;
    @JsonProperty("error_description")
    String errorDescription;

    ResponseData() {
        errorCode = "0000";
        errorMessage = "success";
        errorDescription = "success";
    }

    ResponseData(String errorMessage) {
        errorCode = "0000";
        this.errorMessage = errorMessage;
        errorDescription = "success";
    }

    ResponseData(String errorCode, String errorMessage, String errorDescription) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDescription = errorDescription;
    }

    public static ResponseData success() {
        return new ResponseData();
    }
    public static ResponseData success(String errorMessage) {
        return new ResponseData(errorMessage);
    }

    public static ResponseData failed() {
        return new ResponseData("0001", "request failure", "something went wrong");
    }

    public static ResponseData failed(String errorCode, String errorMessage, String errorDescription) {
        return new ResponseData(errorCode, errorMessage, errorDescription);
    }
}

class ResponseMeta {

    @JsonProperty("server_correlation_id")
    String serverCorrelationId;
    @JsonProperty("request_id")
    String requestId;
    @JsonProperty("timestamp")
    String timeStamp;

    public ResponseMeta(String correlationId, String requestId){
        this.serverCorrelationId = correlationId;
        this.requestId = requestId;
        this.timeStamp = DateTimeWrapper.now("yyyy-MM-dd HH:mm:ss.sss");
    }

    public static ResponseMeta buildMeta() {
        return new ResponseMeta(UuidWrapper.uuidAsString(), UuidWrapper.uuidAsString());
    }

    public static ResponseMeta buildMeta( String correlationId) {
        return new ResponseMeta(correlationId, UuidWrapper.uuidAsString());
    }
}