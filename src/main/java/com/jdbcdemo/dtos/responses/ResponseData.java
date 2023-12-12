package com.jdbcdemo.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"error_code", "error_message","error_description"})
public class ResponseData {
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

    public static ResponseData success(ResponseData customResponseData) {
        customResponseData.errorCode = "0000";
        customResponseData.errorMessage = "success";
        customResponseData.errorDescription = "success";
        return customResponseData;
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
