package kh.com.cellcard.services.tracing;


public interface CorrelationService {
    String getCorrelationId();
    String getRequestId();
}
