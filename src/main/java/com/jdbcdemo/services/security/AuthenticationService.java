package com.jdbcdemo.services.security;

import com.jdbcdemo.common.security.authprovider.apikeyauth.ApiKeyAuthentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.server.ServerWebExchange;

public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "token";

    public static Authentication getAuthentication( ServerWebExchange exchange) {

        String apiKey = exchange
            .getRequest()
            .getHeaders()
            .getFirst(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
