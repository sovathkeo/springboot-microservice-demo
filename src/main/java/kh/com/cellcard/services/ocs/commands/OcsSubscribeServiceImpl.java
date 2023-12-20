package kh.com.cellcard.services.ocs.commands;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OcsSubscribeServiceImpl implements OcsSubscribeService {

    @Override
    public Mono<Boolean> subscribeBundle( String accountId, String bundleId ) {
        return Mono.just(true);
    }
}
