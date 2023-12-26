package kh.com.cellcard.common.configurations.appsetting.smscatalog;

import kh.com.cellcard.common.enums.smscatalog.MessageCatalogLanguage;
import lombok.Setter;

import java.util.HashMap;

@Setter
public class MessageCatalogConfig {
    public String originator;
    public String code;
    public HashMap<String, String> message;
    public String description;

    public String getMessage(MessageCatalogLanguage language) {
        return message.get(language.getKey());
    }
}
