package com.jdbcdemo.common.helper;

import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;

public abstract class HttpResponseHelper {

    public static String getBodyAsString( ContentCachingResponseWrapper res ) {
        return new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);
    }
}
