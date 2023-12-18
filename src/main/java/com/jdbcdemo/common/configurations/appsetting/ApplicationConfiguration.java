package com.jdbcdemo.common.configurations.appsetting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jdbcdemo.Application;
import com.jdbcdemo.common.helper.StringHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "application-config")
@JsonIgnoreProperties(value = {"$$beanFactory"})
@Getter
@Setter
public class ApplicationConfiguration {
    public static final String APPLICATION_NAME = Application.class.getPackageName();

    @Getter
    @Setter
    public GlobalConfig globalConfig;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.name}")
    public String maintenanceMode;

    public String maintenanceMessage;

    private String[] endpointsAuthWhitelist = new String[]{};


    @Getter
    @Setter
    public MicroservicesConfig microservices;

    public String[] getEndpointsAuthWhitelist() {
        return endpointsAuthWhitelist;
    }

    public void setEndpointsAuthWhitelist( String[] endpointsAuthWhitelist ) {
        this.endpointsAuthWhitelist = endpointsAuthWhitelist;
    }

    public String getApplicationName() {
        return StringHelper.isNullOrEmpty(applicationName) ? APPLICATION_NAME : applicationName;
    }

    public void setApplicationName( String applicationName ) {
        this.applicationName = applicationName;
    }

    public String getMaintenanceMessage() {
        return StringHelper.isNullOrEmpty(maintenanceMessage)
                ? this.globalConfig.maintenanceMessage
                : this.maintenanceMessage;
    }

}