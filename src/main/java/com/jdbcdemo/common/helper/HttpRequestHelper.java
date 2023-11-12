package com.jdbcdemo.common.helper;

import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

public abstract class HttpRequestHelper {

    public static String getBodyAsString( ContentCachingRequestWrapper req ) {
        return new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
    }
}
