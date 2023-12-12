package com.jdbcdemo.common.filter;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.StringHelper;
import com.jdbcdemo.common.helper.logging.ApplicationLog;
import com.jdbcdemo.common.wrapper.UuidWrapper;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.Nullable;

import java.io.IOException;
import java.util.Objects;

public class LogFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationConfiguration appSetting;
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
        Objects.requireNonNull(filterChain).doFilter(request, response);
        /*
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(Objects.requireNonNull(request));
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(Objects.requireNonNull(response));

        Objects.requireNonNull(filterChain).doFilter(req, res);

        var logHelper = initializeLogParams(request, HttpRequestHelper.getBodyAsString(req), correlationId);
        logger.info(logHelper.getLogMessage());

        logHelper.action = "Response";
        logHelper.result = "success";
        logger.info(logHelper.getLogMessage());

        res.copyBodyToResponse();*/
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
            .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

    private ApplicationLog initializeLogParams( HttpServletRequest request, String payload, String correlationId) {

        final String serviceName = appSetting.getApplicationName();
        final String methodName = "check eligibility";

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
        return new ApplicationLog(
            serviceName,
            methodName ,
            "",
            clientIp,
            "",
            "85599204681",
            payload,
            correlationService);
    }
}