package kh.com.cellcard.common.interceptor;

import jakarta.annotation.Nullable;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class RestTemplateAddHeaderInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(RestTemplateAddHeaderInterceptor.class);

    @Autowired
    private CorrelationService correlationService;

    @Override
    public ClientHttpResponse intercept( @Nullable HttpRequest request,
                                         @Nullable byte[] body,
                                         @Nullable ClientHttpRequestExecution execution ) throws IOException {
        if (request == null) {
            throw new RuntimeException("http request instance can not be null");
        }


        var correlationId = correlationService.getCorrelationId();

        logger.error("==> correlationId = "+ correlationId);

        request
            .getHeaders()
            .add(HttpHeaderConstant.CORRELATION_ID, correlationId);

        Objects.requireNonNull(body);
        Objects.requireNonNull(execution);

        return execution.execute(request, body);
    }
}
