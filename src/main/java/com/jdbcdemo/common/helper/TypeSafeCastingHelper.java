package com.jdbcdemo.common.helper;

import com.jdbcdemo.common.security.authprovider.apikeyauth.ApiKeyAuthentication;
import com.jdbcdemo.common.security.authprovider.basicauth.BasicAuthentication;
import com.jdbcdemo.common.security.authprovider.bearerauth.BearerAuthentication;

import java.util.Optional;

public class TypeSafeCastingHelper {

    public static Optional<BearerAuthentication> castToBearerAuthentication( Object obj) {
        return (obj instanceof BearerAuthentication ? Optional.of((BearerAuthentication)obj) : Optional.empty());
    }

    public static Optional<BasicAuthentication> castToBasicAuthentication( Object obj) {
        return (obj instanceof BasicAuthentication ? Optional.of((BasicAuthentication)obj) : Optional.empty());
    }

    public static Optional<ApiKeyAuthentication> castToApiKeyAuthentication( Object obj) {
        return (obj instanceof ApiKeyAuthentication ? Optional.of((ApiKeyAuthentication)obj) : Optional.empty());
    }
}
