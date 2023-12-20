package kh.com.cellcard.common.interceptor;

import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.ExchangeFunction;
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
