package com.jdbcdemo.services.tracing;

import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.helper.StringHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Service
public class CorrelationServiceImpl implements CorrelationService{

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public String getCorrelationId() {

        RequestAttributes attr = RequestContextHolder.getRequestAttributes();

        if (attr != null) {

            HttpServletRequest request = ((ServletRequestAttributes) attr).getRequest();

            var existingCorrelationId = request.getHeader(HttpHeaderConstant.CORRELATION_ID);

            if (! StringHelper.isNullOrEmpty(existingCorrelationId)) {
                return existingCorrelationId;
            }

            var correlationId = request.getAttribute(HttpHeaderConstant.CORRELATION_ID);

            if (correlationId != null && StringUtils.hasText(correlationId.toString())) {
                return correlationId.toString();
            }
        }

        return UUID.randomUUID().toString();
    }
}
