package com.jdbcdemo.dtos.responses;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppInfoResponseImpl extends ResponseData {
    private ApplicationConfiguration appSetting;
}
