package kh.com.cellcard.services.ocs.commands;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public interface OcsSubscribeService {

    Mono<Boolean> subscribeBundle(String accountId, String bundleId);
}
