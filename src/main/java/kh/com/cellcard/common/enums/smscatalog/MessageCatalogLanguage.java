package kh.com.cellcard.common.enums.smscatalog;

import lombok.Getter;

@Getter
public enum MessageCatalogLanguage {
    en("en"),
    kh("kh"),
    ch("ch");
    private final String key;
    MessageCatalogLanguage(String key){ this.key = key; }
}
