package com.jdbcdemo.common.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    /*@Bean
    public FilterRegistrationBean<ExceptionFilter> regFilter1( ExceptionFilter exceptionFilter){

        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(exceptionFilter());

        registrationBean.addUrlPatterns("/**");
        registrationBean.setOrder(0);

        return registrationBean;
    }

    @Bean
    public ExceptionFilter exceptionFilter() {
        return new ExceptionFilter();
    }*/
}
