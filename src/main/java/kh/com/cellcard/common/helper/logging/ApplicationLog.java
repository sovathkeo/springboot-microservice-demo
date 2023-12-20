package kh.com.cellcard.common.helper.logging;

import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.common.logging.ApplicationLogger;
import kh.com.cellcard.common.shareobject.ShareObject;
import kh.com.cellcard.common.wrapper.DateTimeWrapper;
import kh.com.cellcard.services.tracing.CorrelationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ApplicationLog extends ShareObject implements ApplicationLogger {

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

    private String transactionTime;
    private String serviceName;
    private String methodName;
    private String correlationId;
    private String requestPlan;
    private String transactionId;
    private String requestId;
    private String clientIp;
    private String channel;
    private String accountId;
    public String action;
    public String payload;
    public String result;
    public String step;
    public String nei;
    public String api;
    public String errorCode;
    public String errorMessage;

    public ApplicationLog(
        String serviceName,
        String methodName,
        String requestPlan,
        String clientIp,
        String channel,
        String accountId,
        String payload,
        CorrelationService correlationService ) {

        this.serviceName = serviceName;
        this.methodName = methodName;
        this.correlationId = correlationService.getCorrelationId();
        this.requestPlan = requestPlan;
        this.requestId = correlationService.getRequestId();
        this.clientIp = clientIp;
        this.channel = channel;
        this.accountId = accountId;
        this.payload = payload;

        this.transactionId = "";
        this.action = "Start";
        this.step = "0";
        this.nei = serviceName;
        this.api = methodName;
        this.result = "";
        this.errorCode = "";
        this.errorMessage = "";
    }

    public void initLogParams(
        String serviceName,
        String methodName,
        String requestPlan,
        String clientIp,
        String channel,
        String accountId,
        String payload,
        CorrelationService correlationService ) {

        this.serviceName = serviceName;
        this.methodName = methodName;
        this.correlationId = correlationService.getCorrelationId();
        this.requestPlan = requestPlan;
        this.requestId = correlationService.getRequestId();
        this.clientIp = clientIp;
        this.channel = channel;
        this.accountId = accountId;
        this.payload = payload;

        this.transactionId = "";
        this.action = "Start";
        this.step = "0";
        this.nei = serviceName;
        this.api = methodName;
        this.result = "";
        this.errorCode = "";
        this.errorMessage = "";
    }

    public Map<String, String> getLogParams() {
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
            { put(LogFormatterKeyHelper.error_code.getKey(), errorCode); }
            { put(LogFormatterKeyHelper.error_message.getKey(), errorMessage); }
            { put(LogFormatterKeyHelper.payload.getKey(), payload); }
            { put(LogFormatterKeyHelper.step.getKey(), step); }
            { put(LogFormatterKeyHelper.nei.getKey(), nei); }
            { put(LogFormatterKeyHelper.api.getKey(), api); }
        };
    }

    @Override
    public void setLogParams(String action,
                             String step,
                             String nei,
                             String result,
                             String api,
                             String errorCode,
                             String errorMessage){
        this.action = StringHelper.getValueOrEmpty(action);
        this.step = StringHelper.getValueOrEmpty(step);
        this.nei = StringHelper.getValueOrEmpty(nei);
        this.result = StringHelper.getValueOrEmpty(result);
        this.api = StringHelper.getValueOrEmpty(api);
        this.errorCode = StringHelper.getValueOrEmpty(errorCode);
        this.errorMessage = StringHelper.getValueOrEmpty(errorMessage);
    }

    @Override
    public void setRequestLogParams(String step, String nei, String api){
        this.action = "Request";
        this.step = StringHelper.getValueOrEmpty(step);
        this.nei = StringHelper.getValueOrEmpty(nei);
        this.result = "";
        this.api = StringHelper.getValueOrEmpty(api);
        this.errorCode = "";
        this.errorMessage = "";
    }

    @Override
    public void setResponseLogParams(String step, String nei, String api, String result, String errorCode, String errorMessage){
        this.action = "Response";
        this.step = StringHelper.getValueOrEmpty(step);
        this.nei = StringHelper.getValueOrEmpty(nei);
        this.result = StringHelper.getValueOrEmpty(result);
        this.api = StringHelper.getValueOrEmpty(api);
        this.errorCode = StringHelper.getValueOrEmpty(errorCode);
        this.errorMessage = StringHelper.getValueOrEmpty(errorMessage);
    }

    public void setLastResponseLogParams(String step, String nei, String api, String result, String errorCode, String errorMessage){
        this.action = "response";
        this.step = StringHelper.getValueOrEmpty(step);
        this.nei = StringHelper.getValueOrEmpty(nei);
        this.result = StringHelper.getValueOrEmpty(result);
        this.api = StringHelper.getValueOrEmpty(api);
        this.errorCode = StringHelper.getValueOrEmpty(errorCode);
        this.errorMessage = StringHelper.getValueOrEmpty(errorMessage);
    }



    public String getLogMessage() {
        String logMessageTmp = this.logMessage;
        var hmLog = getLogParams();
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