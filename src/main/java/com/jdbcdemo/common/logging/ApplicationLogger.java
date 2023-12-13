package com.jdbcdemo.common.logging;

public interface ApplicationLogger {
    void setLogParams(String action,
                      String step,
                      String nei,
                      String result,
                      String api,
                      String errorCode,
                      String errorMessage);

    void setRequestLogParams(String step, String nei, String api);

    void setResponseLogParams( String step, String nei, String api, String result, String errorCode, String errorMessage );
}
