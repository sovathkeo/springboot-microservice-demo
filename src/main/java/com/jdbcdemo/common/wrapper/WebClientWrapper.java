package com.jdbcdemo.common.wrapper;

import com.jdbcdemo.common.interceptor.ExchangeInterceptorFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public WebClient.Builder useApiKeyAuthentication(String keyName, String keyValue) {
        return webClientBuilder
            .filter((req, next) -> ExchangeInterceptorFunctions.apiKeyAuthentication(req, next, keyName, keyValue));
    }

    public WebClient.Builder useBasicAuthentication(String username, String password) {
        return webClientBuilder
            .filter(ExchangeFilterFunctions.basicAuthentication(username, password));
    }

    public ResponseEntity<?> postSync(String url, Object payload) {
        return webClientBuilder
            .build()
            .post()
            .uri(url)
            .bodyValue(payload)
            .retrieve()
            .toEntity(Object.class)
            .block();
    }
}
