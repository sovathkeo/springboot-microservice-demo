package com.jdbcdemo.common.filter;

import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.HttpRequestHelper;
import com.jdbcdemo.common.helper.StringHelper;
import com.jdbcdemo.common.helper.logging.LogFormatterHelper;
import com.jdbcdemo.common.wrapper.UuidWrapper;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Objects;

public class LogFilter extends OncePerRequestFilter {

    @Autowired
    private CorrelationService correlationService;

    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain ) throws ServletException, IOException {

        configureInjectionBasedOnServletContext(request);
        var correlationId = correlationService.getCorrelationId();
        MDC.put(HttpHeaderConstant.CORRELATION_ID,correlationId);

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(Objects.requireNonNull(request));
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(Objects.requireNonNull(response));

        Objects.requireNonNull(filterChain).doFilter(req, res);

        var logHelper = initializeLogParams(request, HttpRequestHelper.getBodyAsString(req), correlationId);
        logger.info(logHelper.getLogMessage());

        logHelper.action = "Response";
        logHelper.result = "success";
        logger.info(logHelper.getLogMessage());

        res.copyBodyToResponse();
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
            .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

    private LogFormatterHelper initializeLogParams(HttpServletRequest request, String payload, String correlationId) {

        final String serviceName = "WingBank Treasure Hunt";
        final String methodName = "check eligibility";

        final String accountId = "855123456";
        String clientIp = request.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);
        String requestId = request.getHeader(HttpHeaderConstant.X_CELLCARD_REQUEST_ID);
        if (StringHelper.isNullOrEmpty(requestId)) {
            requestId = correlationId;
        }
        if (StringHelper.isNullOrEmpty(requestId)) {
            requestId = UuidWrapper.uuidAsString();
        }
        if(clientIp == null) clientIp = request.getRemoteUser();
        // common log declaration
        return new LogFormatterHelper(
                serviceName,
                methodName ,
                correlationId,
                "",
                correlationId,
                requestId,
                clientIp,
                "",
                accountId,
                payload,
                "Request");
    }
}