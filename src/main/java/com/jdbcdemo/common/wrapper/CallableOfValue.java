package com.jdbcdemo.common.wrapper;

@FunctionalInterface
public interface CallableOfValue {
    void callback( Object value);
}
