package kh.com.cellcard.common.helper.logging;

public enum LogFormatterKeyHelper {

    service_name("service_name"),
    account_id("account_id"),
    method_name("method_name"),
    request_plan("request_plan"),
    channel("channel"),
    request_id("request_id"),
    correlation_id("correlation_id"),
    transaction_id("transaction_id"),
    uuid("uuid"),
    tariff_plan_cosp_id("tariff_plan_cosp_id"),
    class_of_service_id("class_of_service_id"),
    api("api"),
    step("step"),
    action("action"),
    result("result"),
    error_code("error_code"),
    error_message("error_message"),
    nei("nei"),
    client_ip("client_ip"),
    payload("payload"),
    transaction_time("transaction_time");

    private final String key;
    LogFormatterKeyHelper(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
