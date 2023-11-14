package com.jdbcdemo.common.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jdbcdemo.Application;
import com.jdbcdemo.common.helper.StringHelper;
import com.jdbcdemo.dtos.base.AResponseBase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application-config")
@JsonIgnoreProperties(value = {"$$beanFactory"})
public class ApplicationConfiguration extends AResponseBase {

    public static final String APPLICATION_NAME = Application.class.getPackageName();

    private String applicationName;

    private String[] endpointsAuthWhitelist = new String[]{};


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
