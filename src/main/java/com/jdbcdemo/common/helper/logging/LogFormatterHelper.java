package com.jdbcdemo.common.helper.logging;

import com.jdbcdemo.common.wrapper.DateTimeWrapper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LogFormatterHelper {

    private final String logMessage = "action = , "
            .concat("service_name = , ")
            .concat("account_id = , ")
            .concat("source_account_id = , ")
            .concat("target_account_id = , ")
            .concat("method_name = , ")
            .concat("request_plan = , ")
            .concat("old_plan = , ")
            .concat("purchase_fee = , ")
            .concat("sale_id = , ")
            .concat("channel = , ")
            .concat("request_id = , ")
            .concat("transaction_id = , ")
            .concat("uuid = , ")
            .concat("tariff_plan_cosp_id = , ")
            .concat("class_of_service_id = , ")
            .concat("api = , ")
            .concat("step = , ")
            .concat("result = , ")
            .concat("error_code = , ")
            .concat("error_message = , ")
            .concat("nei = , ")
            .concat("server_host = , ")
            .concat("client_ip = , ")
            .concat("username = ,")
            .concat("transaction_time = ");

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
    public String payload;

    public String result;

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

    public LogFormatterHelper(
            String serviceName,
            String methodName,
            String correlationId,
            String requestPlan,
            String transactionId,
            String requestId,
            String clientIp,
            String channel,
            String accountId,
            String payload,
            String action) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.correlationId = correlationId;
        this.requestPlan = requestPlan;
        this.transactionId = transactionId;
        this.requestId = requestId;
        this.clientIp = clientIp;
        this.channel = channel;
        this.accountId = accountId;
        this.action = action;
        this.payload = payload;
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
            { put(LogFormatterKeyHelper.action.getKey(), action); }
            { put(LogFormatterKeyHelper.result.getKey(), result); }
            { put(LogFormatterKeyHelper.payload.getKey(), payload); }
        };
    }

    public String getLogMessage() {
        String logMessageTmp = this.logMessage;
        var hmLog = getBasicParamLog();
        try {
            for(String key : hmLog.keySet()){
                String value = hmLog.get(key);
                if (value != null) {
                    value = value.replaceAll("\\$", "#").replaceAll(",", "#");
                    logMessageTmp = logMessageTmp.replaceFirst(key.concat(" = "), key.concat(" = ").concat(value));
                }
            }
        } catch(Exception ignored) {

        }

        return logMessageTmp;
    }
}
