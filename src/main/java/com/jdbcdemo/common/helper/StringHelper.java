package com.jdbcdemo.common.helper;

public abstract class StringHelper {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty() || s.isBlank();
    }
}
