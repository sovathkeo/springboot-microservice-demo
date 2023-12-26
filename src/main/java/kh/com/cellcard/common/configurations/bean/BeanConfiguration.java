package kh.com.cellcard.common.configurations.bean;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.interceptor.ExchangeInterceptorFunctions;
import kh.com.cellcard.common.interceptor.RestTemplateAddHeaderInterceptor;
import kh.com.cellcard.services.tracing.CorrelationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class BeanConfiguration {

    @Autowired ApplicationConfiguration appSetting;

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

    private ReactorClientHttpConnector reactorClientHttpConnector() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, appSetting.globalConnectTimeoutMillisecond)
                .responseTimeout(Duration.ofMillis(appSetting.globalRequestTimeoutMillisecond));
        return new ReactorClientHttpConnector(httpClient);
    }
}
