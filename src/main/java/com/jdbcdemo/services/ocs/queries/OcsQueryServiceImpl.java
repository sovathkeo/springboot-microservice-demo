package com.jdbcdemo.services.ocs.queries;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OcsQueryServiceImpl  implements OcsQueryService {

    @Override
    public Mono<String> querySubscriberAccount( String accountId ) {
        return Mono.just("success query subscriber account");
    }
}
