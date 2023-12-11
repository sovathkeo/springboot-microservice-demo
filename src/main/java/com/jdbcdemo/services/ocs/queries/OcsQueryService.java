package com.jdbcdemo.services.ocs.queries;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface OcsQueryService {

    Mono<String> querySubscriberAccount(String accountId);
}