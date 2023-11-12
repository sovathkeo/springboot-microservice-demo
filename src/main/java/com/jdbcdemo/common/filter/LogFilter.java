package com.jdbcdemo.common.filter;

import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.HttpRequestHelper;
import com.jdbcdemo.common.helper.HttpResponseHelper;
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

        logger.info("request body = " + HttpRequestHelper.getBodyAsString(req));
        logger.info("response = " + HttpResponseHelper.getBodyAsString(res));
        res.copyBodyToResponse();
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
            .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

}