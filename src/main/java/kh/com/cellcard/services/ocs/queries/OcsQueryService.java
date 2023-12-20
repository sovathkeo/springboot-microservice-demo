package kh.com.cellcard.services.ocs.queries;

import kh.com.cellcard.models.ocs.OcsQueryAccountResponseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface OcsQueryService {
    Mono<Optional<OcsQueryAccountResponseModel>> querySubscriberAccount(String accountId);
}