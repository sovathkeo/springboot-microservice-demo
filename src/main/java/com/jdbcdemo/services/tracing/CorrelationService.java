package com.jdbcdemo.services.tracing;


public interface CorrelationService {
    String getCorrelationId();
    String getRequestId();
}
