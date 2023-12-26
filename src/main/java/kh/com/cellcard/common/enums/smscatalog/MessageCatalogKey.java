package kh.com.cellcard.common.enums.smscatalog;

import lombok.Getter;

@Getter
public enum MessageCatalogKey {
    success("success"),
    not_enough_balance("not-enough-balance");

    private final String key;
    MessageCatalogKey(String key){
        this.key = key;
    }

}
