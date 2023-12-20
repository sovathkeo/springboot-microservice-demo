package kh.com.cellcard.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.common.helper.logging.ApplicationLog;
import kh.com.cellcard.services.tracing.CorrelationService;
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
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
            .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

    private ApplicationLog initializeLogParams(HttpServletRequest request, String payload, String correlationId) {

        final String serviceName = appSetting.getApplicationName();
        final String methodName = "default";

        String clientIp = request.getHeader(HttpHeaderConstant.X_FORWARDED_FOR);

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