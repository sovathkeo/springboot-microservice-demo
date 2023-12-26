
package kh.com.cellcard.common.wrapper;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.interceptor.ExchangeInterceptorFunctions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Getter
@Component
public class WebClientWrapper {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ApplicationConfiguration appSetting;

    @Setter
    private int REQUEST_TIME_OUT;

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
                .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(Object.class)
            .timeout(Duration.ofMillis(this.getRequestTimeout()))
            .block();
    }

    public Mono<ResponseEntity<String>> getAsync( String url) {
        return  webClientBuilder
            .build()
            .get()
            .uri(url)
            .retrieve()
            .toEntity(String.class)
            .timeout(Duration.ofMillis(this.getRequestTimeout()))
        ;
    }

    public ResponseEntity<String> post(String url, Object payload) {

        return  webClientBuilder
            .build()
            .post()
            .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve()
            .toEntity(String.class)
            .timeout(Duration.ofMillis(this.getRequestTimeout()))
            .block();
    }

    public Mono<ResponseEntity<String>> postJsonAsync(String url, Object payload) {
        return  webClientBuilder
                .build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .toEntity(String.class)
                .timeout(Duration.ofMillis(this.getRequestTimeout()));
    }

    public Mono<ResponseEntity<String>> postXmlAsync(String url, Object payload) {

        return  webClientBuilder
            .build()
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_XML)
            .bodyValue(payload)
            .retrieve()
            .toEntity(String.class)
            .timeout(Duration.ofMillis(this.getRequestTimeout()));
    }

    private int getRequestTimeout() {
        return REQUEST_TIME_OUT < 1
            ? appSetting.globalRequestTimeoutMillisecond
            : REQUEST_TIME_OUT;
    }
}
