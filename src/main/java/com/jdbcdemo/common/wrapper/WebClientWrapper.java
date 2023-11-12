
package com.jdbcdemo.common.wrapper;

import com.jdbcdemo.common.interceptor.ExchangeInterceptorFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientWrapper {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public WebClient.Builder getWebClientBuilder() {
        return webClientBuilder;
    }

    public WebClientWrapper useApiKeyAuthentication( String keyName, String keyValue) {
        webClientBuilder
            .filter((req, next) -> ExchangeInterceptorFunctions.apiKeyAuthentication(req, next, keyName, keyValue));
        return this;
    }

    public WebClientWrapper useBasicAuthentication(String username, String password) {
        webClientBuilder
            .filter(ExchangeFilterFunctions.basicAuthentication(username, password));
        return this;
    }


    public ResponseEntity<?> getSync(String url) {

        return  webClientBuilder
            .build()
            .get()
            .uri(url)
            .retrieve()
            .toEntity(Object.class)
            .block();
    }

    public Mono<ResponseEntity<Object>> getAsync( String url) {
        return  webClientBuilder
            .build()
            .get()
            .uri(url)
            .retrieve()
            .toEntity(Object.class);
    }

    public ResponseEntity<?> postSync(String url, Object payload) {

        return  webClientBuilder
            .build()
            .post()
            .uri(url)
            .bodyValue(payload)
            .retrieve()
            .toEntity(Object.class)
            .block();
    }

    public Mono<ResponseEntity<Object>> postAsync( String url, Object payload) {
        return  webClientBuilder
            .build()
            .post()
            .uri(url)
            .bodyValue(payload)
            .retrieve()
            .toEntity(Object.class);
    }
}
