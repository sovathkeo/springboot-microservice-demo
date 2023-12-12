package com.jdbcdemo.common.configurations.appsetting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jdbcdemo.Application;
import com.jdbcdemo.common.helper.StringHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application-config")
@JsonIgnoreProperties(value = {"$$beanFactory"})
public class ApplicationConfiguration {

    public static final String APPLICATION_NAME = Application.class.getPackageName();

    @Value("${spring.application.name}")
    private String applicationName;

    private String[] endpointsAuthWhitelist = new String[]{};


    @Getter
    @Setter
    private MicroservicesConfig microservices;

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



}