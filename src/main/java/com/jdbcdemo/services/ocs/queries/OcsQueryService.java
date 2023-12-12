package com.jdbcdemo.services.ocs.queries;

import com.jdbcdemo.models.ocs.OcsQueryAccountResponseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface OcsQueryService {
    Mono<Optional<OcsQueryAccountResponseModel>> querySubscriberAccount(String accountId);
}