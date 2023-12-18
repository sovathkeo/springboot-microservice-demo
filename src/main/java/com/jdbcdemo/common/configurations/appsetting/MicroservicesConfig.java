package com.jdbcdemo.common.configurations.appsetting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroservicesConfig {
    public MicroserviceConfig ocsService;

    public SmsPushServiceConfig smsService;
}
