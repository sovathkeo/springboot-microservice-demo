package kh.com.cellcard.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.maintenance.MaintenanceService;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

public class MaintenanceModeFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationConfiguration appSetting;

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

        if (!isBypassAuth(request) && maintenanceService.isUnderMaintenance()) {
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
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void configureInjectionBasedOnServletContext(HttpServletRequest request) {
        SpringBeanAutowiringSupport
                .processInjectionBasedOnServletContext(this, Objects.requireNonNull(request).getServletContext());
    }

    private boolean isBypassAuth(HttpServletRequest request) {
        return isInAuthWhitelist(request) || isMonitoringRequest(request);
    }

    private boolean isInAuthWhitelist(HttpServletRequest request) {
        var requestUri = request.getRequestURI();
        return Arrays.stream(appSetting.getEndpointsAuthWhitelist())
                .anyMatch(requestUri::startsWith);
    }

    private boolean isMonitoringRequest( HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

}
