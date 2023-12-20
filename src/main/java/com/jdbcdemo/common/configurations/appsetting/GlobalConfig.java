package com.jdbcdemo.common.configurations.appsetting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@Setter
public class GlobalConfig {
    public String maintenanceMode;
    public String availableAt;
    public String maintenanceMessage;
}
