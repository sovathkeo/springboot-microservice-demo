package kh.com.cellcard.common.helper.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public abstract class HttpRequestHelper {

    public static String getBodyAsString( ContentCachingRequestWrapper req ) {
        return new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    public static String getBodyAsString( HttpServletRequest request ) {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(Objects.requireNonNull(request));
        return new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
    }
}