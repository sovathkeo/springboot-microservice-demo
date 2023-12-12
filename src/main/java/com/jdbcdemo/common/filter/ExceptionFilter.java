package com.jdbcdemo.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.exceptions.models.ApplicationError;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        var requestId = correlationService.getRequestId();
        try {

            Objects.requireNonNull(request).setAttribute(HttpHeaderConstant.CORRELATION_ID, correlationId);
            Objects.requireNonNull(request).setAttribute(HttpHeaderConstant.X_CELLCARD_REQUEST_ID, requestId);
            Objects.requireNonNull(response).addHeader(HttpHeaderConstant.CORRELATION_ID,correlationId);
            Objects.requireNonNull(filterChain).doFilter(request, response);

        } catch (Exception exp) {

            Objects.requireNonNull(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            var res = Response.failure(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED),"Unauthorized",exp.getMessage(),correlationId);

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
