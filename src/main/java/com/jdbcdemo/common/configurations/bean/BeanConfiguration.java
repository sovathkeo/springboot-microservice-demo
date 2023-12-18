package com.jdbcdemo.common.configurations.bean;

import com.jdbcdemo.common.interceptor.ExchangeInterceptorFunctions;
import com.jdbcdemo.common.interceptor.RestTemplateAddHeaderInterceptor;
import com.jdbcdemo.common.wrapper.WebClientWrapper;
import com.jdbcdemo.services.tracing.CorrelationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
public class BeanConfiguration {

    @Value("${spring.datasource.url}")
    String dbUrl;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate( RestTemplateBuilder builder, RestTemplateAddHeaderInterceptor restTemplateAddHeaderInterceptor) {
        var restTemplate = builder
            .build();

        restTemplate.setInterceptors(Collections.singletonList(restTemplateAddHeaderInterceptor));

        return restTemplate;
    }

    @Bean
    public WebClient webClient( CorrelationService correlationService ) {
        return WebClient
            .builder()
            .filter((req, next) -> ExchangeInterceptorFunctions.addCorrelationIdHeader(req,correlationService, next))
            .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder( CorrelationService correlationService ) {
        return WebClient
            .builder()
            .filter((req, next) -> ExchangeInterceptorFunctions.addCorrelationIdHeader(req,correlationService, next));
    }
}
