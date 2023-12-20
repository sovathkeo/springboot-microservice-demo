package kh.com.cellcard.services.tracing;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.common.helper.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Service
public class CorrelationServiceImpl implements CorrelationService {

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

    @Override
    public String getRequestId() {

        RequestAttributes attr = RequestContextHolder.getRequestAttributes();

        if (attr != null) {

            HttpServletRequest request = ((ServletRequestAttributes) attr).getRequest();

            var existing = request.getHeader(HttpHeaderConstant.X_CELLCARD_REQUEST_ID);

            if (! StringHelper.isNullOrEmpty(existing)) {
                return existing;
            }

            var requestId = request.getAttribute(HttpHeaderConstant.X_CELLCARD_REQUEST_ID);

            if (requestId != null && StringUtils.hasText(requestId.toString())) {
                return requestId.toString();
            }
        }

        return UUID.randomUUID().toString();
    }
}
