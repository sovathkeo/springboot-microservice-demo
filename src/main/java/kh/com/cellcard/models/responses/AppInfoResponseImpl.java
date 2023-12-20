package kh.com.cellcard.models.responses;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppInfoResponseImpl {
    private ApplicationConfiguration appSetting;
}
