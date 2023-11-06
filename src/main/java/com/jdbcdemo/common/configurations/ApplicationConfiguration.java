package com.jdbcdemo.common.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application-config")
public class ApplicationConfiguration {

    private String[] endpointsAuthWhitelist;


    public String[] getEndpointsAuthWhitelist() {
        return endpointsAuthWhitelist;
    }

    public void setEndpointsAuthWhitelist( String[] endpointsAuthWhitelist ) {
        this.endpointsAuthWhitelist = endpointsAuthWhitelist;
    }
}
