package kh.com.cellcard.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.com.cellcard.common.wrapper.DateTimeWrapper;
import kh.com.cellcard.common.wrapper.UuidWrapper;
import kh.com.cellcard.services.tracing.CorrelationService;

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

    public static ResponseMeta buildMeta( CorrelationService correlationService ) {
        return new ResponseMeta(correlationService.getCorrelationId(), correlationService.getRequestId());
    }

    public static ResponseMeta buildMeta( String correlationId, String requestId) {
        return new ResponseMeta(correlationId, requestId);
    }
}