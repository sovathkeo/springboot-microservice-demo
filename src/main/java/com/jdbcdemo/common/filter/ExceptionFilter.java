package com.jdbcdemo.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.exceptions.ApplicationError;
import com.jdbcdemo.dtos.responses.ResponseImpl;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CorrelationService correlationService;

    @Override
    protected void doFilterInternal(@NotNull @Nullable HttpServletRequest request,
                                    @NotNull @Nullable HttpServletResponse response,
                                    @NotNull @Nullable FilterChain filterChain) throws IOException {

        configureInjectionBasedOnServletContext(request);
        var correlationId = correlationService.getCorrelationId();

        try {

            Objects.requireNonNull(request).setAttribute(HttpHeaderConstant.CORRELATION_ID, correlationId);
            MDC.put(HttpHeaderConstant.CORRELATION_ID,correlationId);
            Objects.requireNonNull(response).addHeader(HttpHeaderConstant.CORRELATION_ID,correlationId);
            Objects.requireNonNull(filterChain).doFilter(request, response);

        } catch (Exception exp) {

            Objects.requireNonNull(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            var res = ResponseImpl.Failed(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized",
                    new ApplicationError(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), exp.getMessage()),
                    correlationId
                );

            var body = mapper.writeValueAsString(res);

            PrintWriter writer = response.getWriter();

            writer.print(body);
            writer.flush();
            writer.close();

        }
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
            .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

}
