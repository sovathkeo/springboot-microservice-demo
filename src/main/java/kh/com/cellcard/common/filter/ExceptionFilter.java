package kh.com.cellcard.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.models.responses.Response;
import kh.com.cellcard.services.tracing.CorrelationService;
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
