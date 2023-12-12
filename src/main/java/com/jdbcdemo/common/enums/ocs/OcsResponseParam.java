package com.jdbcdemo.common.enums.ocs;

public enum OcsResponseParam {
    SUCCESS_RESULT("SUCCESS"),
    FAILURE_RESULT("FAILURE");

    private final String key;

    OcsResponseParam(String key){ this.key = key; }

    public String getKey() {
        return key;
    }
}
