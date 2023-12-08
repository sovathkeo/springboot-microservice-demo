package com.jdbcdemo.common.helper.logging;

import com.jdbcdemo.common.wrapper.DateTimeWrapper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LogFormatterHelper {

    private Map<String, String> hmLogParam;

    private String transactionTime;
    private final String serviceName;
    private final String methodName;
    private final String correlationId;
    private final String requestPlan;
    private final String transactionId;
    private final String requestId;
    private final String clientIp;
    private final String channel;
    private final String accountId;
    public String action;

    SimpleDateFormat formatter = new SimpleDateFormat(DateTimeWrapper.dateTimeFormat1);

    public LogFormatterHelper(String serviceName, String methodName, String correlationId, String requestPlan, String transactionId, String requestId, String clientIp, String channel, String accountId) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.correlationId = correlationId;
        this.requestPlan = requestPlan;
        this.transactionId = transactionId;
        this.requestId = requestId;
        this.clientIp = clientIp;
        this.channel = channel;
        this.accountId = accountId;
    }

    public LogFormatterHelper(String serviceName, String methodName, String correlationId, String requestPlan, String transactionId, String requestId, String clientIp, String channel, String accountId, String payload) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.correlationId = correlationId;
        this.requestPlan = requestPlan;
        this.transactionId = transactionId;
        this.requestId = requestId;
        this.clientIp = clientIp;
        this.channel = channel;
        this.accountId = accountId;
        this.action = "";
    }

    public Map<String, String> getBasicParamLog() {
        return new HashMap<>(){
            { put(LogFormatterKeyHelper.transaction_time.getKey(), DateTimeWrapper.now(DateTimeWrapper.dateTimeFormat1)); }
            { put(LogFormatterKeyHelper.service_name.getKey(), serviceName); }
            { put(LogFormatterKeyHelper.method_name.getKey(), methodName); }
            { put(LogFormatterKeyHelper.correlation_id.getKey(), correlationId); }
            { put(LogFormatterKeyHelper.request_plan.getKey(), requestPlan); }
            { put(LogFormatterKeyHelper.transaction_id.getKey(), transactionId); }
            { put(LogFormatterKeyHelper.request_id.getKey(), requestId); }
            { put(LogFormatterKeyHelper.client_ip.getKey(), clientIp); }
            { put(LogFormatterKeyHelper.channel.getKey(), channel); }
            { put(LogFormatterKeyHelper.account_id.getKey(), accountId); }
        };
    }
}
