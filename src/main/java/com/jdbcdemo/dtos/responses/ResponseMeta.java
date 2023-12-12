package com.jdbcdemo.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jdbcdemo.common.wrapper.DateTimeWrapper;
import com.jdbcdemo.common.wrapper.UuidWrapper;

public class ResponseMeta {

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