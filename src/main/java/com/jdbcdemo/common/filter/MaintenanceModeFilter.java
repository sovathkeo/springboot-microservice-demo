package com.jdbcdemo.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.models.responses.Response;
import com.jdbcdemo.services.maintenance.MaintenanceService;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class MaintenanceModeFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private CorrelationService correlationService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        configureInjectionBasedOnServletContext(request);

        if (maintenanceService.isUnderMaintenance()) {
            Objects.requireNonNull(response).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            var res = Response.failure(
                    String.valueOf(HttpServletResponse.SC_SERVICE_UNAVAILABLE),
                    maintenanceService.getMaintenanceMessage(),
                    "Service under maintenance!",
                    correlationService);

            var body = mapper.writeValueAsString(res);

            PrintWriter writer = response.getWriter();

            writer.print(body);
            writer.flush();
            writer.close();
        }
        filterChain.doFilter(request, response);
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
                .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

}
