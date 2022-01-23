package resilience4j.micronaut.demo.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Service {
    Flux<String> fluxSuccess();

    Flux<String> fluxFailure();

    Mono<String> monoSuccess();

    Mono<String> monoFailure();
}
