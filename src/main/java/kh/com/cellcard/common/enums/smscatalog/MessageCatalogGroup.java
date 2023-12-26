package kh.com.cellcard.common.enums.smscatalog;

import lombok.Getter;

@Getter
public enum MessageCatalogGroup {
    system_error("system-error"),
    invalid_account_id("invalid-account-id"),
    invalid_request_plan("invalid-request-plan"),
    subscribe_success("subscribe-success"),
    unsubscribe("unsubscribe");

    private final String key;
    MessageCatalogGroup(String key){ this.key = key; }
}