package com.jdbcdemo.common.interceptor;

import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

public abstract class ExchangeInterceptorFunctions extends ExchangeFilterFunctions {

    public static Mono<ClientResponse> addCorrelationIdHeader(
        ClientRequest request,
        CorrelationService correlationService,
        ExchangeFunction next) {

        var cloneReq = ClientRequest.from(request);
        cloneReq.headers(x -> x.add(HttpHeaderConstant.CORRELATION_ID, correlationService.getCorrelationId()));

        return next.exchange(cloneReq.build());
    }

    public static Mono<ClientResponse> apiKeyAuthentication(
        ClientRequest request,
        ExchangeFunction next,
        String headerName,
        String key) {
        var cloneReq = ClientRequest
            .from(request)
            .header(headerName, key)
            .build();
        return next.exchange(cloneReq);
    }
}
