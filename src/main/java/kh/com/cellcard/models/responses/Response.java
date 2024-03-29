package kh.com.cellcard.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kh.com.cellcard.services.tracing.CorrelationService;

import java.io.Serializable;


@JsonIgnoreProperties(value = {"$$beanFactory"})
public class Response {
    @JsonProperty("meta")
    ResponseMeta meta;

    @JsonProperty("data")
    ResponseData data;

    protected Response() {
        this.meta = ResponseMeta.buildMeta();
        this.data = ResponseData.success();
    }

    protected Response(Object additionalData, CorrelationService correlationService) {
        this.meta = ResponseMeta.buildMeta(correlationService);
        this.data = ResponseData.success();
        this.data.additionalData = additionalData;
    }

    protected Response(Object additionalData) {
        this.meta = ResponseMeta.buildMeta();
        this.data = ResponseData.success();
        this.data.additionalData = additionalData;
    }

    protected Response(String errorMessage, Object additionalData, CorrelationService correlationService) {
        this.meta = ResponseMeta.buildMeta(correlationService);
        this.data = ResponseData.success(errorMessage);
        this.data.additionalData = additionalData;
    }

    protected Response(String errorMessage) {
        this.meta = ResponseMeta.buildMeta();
        this.data = ResponseData.success(errorMessage);
    }

    protected Response(String errorMessage, CorrelationService correlationService) {
        this.meta = ResponseMeta.buildMeta(correlationService);
        this.data = ResponseData.success(errorMessage);
    }


    protected Response(String errorCode, String errorMessage, String errorDescription, String correlationId) {
        this.meta = ResponseMeta.buildMeta(correlationId);
        this.data = ResponseData.failed(errorCode, errorMessage, errorDescription);
    }

    protected Response(String errorCode, String errorMessage, String errorDescription, CorrelationService correlationService) {
        this.meta = ResponseMeta.buildMeta(correlationService);
        this.data = ResponseData.failed(errorCode, errorMessage, errorDescription);
    }

    protected Response(String errorCode, String errorMessage, String errorDescription, String correlationId, String requestId) {
        this.meta = ResponseMeta.buildMeta(correlationId, requestId);
        this.data = ResponseData.failed(errorCode, errorMessage, errorDescription);
    }

    public static Response success() {
        return new Response();
    }

    public static Response success(Object additionalData) {
        return new Response(additionalData);
    }

    public static Response success(
        String errorMessage,
        Object additionalData,
        CorrelationService correlationService) {
        return new Response(errorMessage, additionalData, correlationService);
    }

    public static Response success(String errorMessage) {
        return new Response(errorMessage);
    }

    public static Response success(String errorMessage, CorrelationService correlationService) {
        return new Response(errorMessage, correlationService);
    }

    // Start build failed response

    public static Response failure(String errorCode, String errorMessage, String errorDescription, CorrelationService correlationService) {
        return new Response(errorCode, errorMessage, errorDescription, correlationService);
    }

    public static Response failure(String errorCode, String errorMessage, String errorDescription, String correlationId) {
        return new Response(errorCode, errorMessage, errorDescription, correlationId);
    }

    // End build failed response


    public void setRequestId(String requestId) {
        this.meta.requestId = requestId;
    }

    public void setMeta(CorrelationService correlationService) {
        this.meta = ResponseMeta.buildMeta(correlationService);
    }
}

