package kh.com.cellcard.models.billing.payload;

import kh.com.cellcard.common.wrapper.DateTimeWrapper;

public abstract class BillingPayloadModel {

    public static String fetchProfile(String accountId, String source){
        var requestId = DateTimeWrapper.getTimestampMillis();
        return """
                {
                    "BillingSystem": {
                        "request_id": "%s",
                        "request_timestamp": "%s",
                        "source": "%s",
                        "action": "FetchProfile",
                        "request": {
                            "service": {
                                "show_balance": "false",
                                "show_address": "false",
                                "show_itemized_preferences": "false",
                                "service_id": "%s"
                            }
                        }
                    }
                }
                """.formatted(requestId, requestId, source, accountId);
    }

    public static String crmFetchProfile(String accountId){
        return fetchProfile(accountId, "CRM");
    }
}
