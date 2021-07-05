package resilience4j.micronaut.demo.service;

import io.github.resilience4j.micronaut.annotation.RateLimiter;
import io.micronaut.context.annotation.Executable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton()
@Named("businessAService")
public class BusinessAService implements Service {
    private static final String BACKEND_A = "backendA";

    @Override
    @RateLimiter(name = BACKEND_A, fallbackMethod = "fluxFallback")
    public Flux<String> fluxSuccess() {
        return Flux.just("Hello", "World");
    }

    @Override
    @RateLimiter(name = BACKEND_A, fallbackMethod = "fluxFallback")
    public Flux<String> fluxFailure() {
        return Flux.error(new IOException("BAM!"));
    }

    @Override
    @RateLimiter(name = BACKEND_A, fallbackMethod = "monoFallback")
    public Mono<String> monoSuccess() {
        return Mono.just("Hello World from backend A");
    }

    @Override
    @RateLimiter(name = BACKEND_A, fallbackMethod = "monoFallback")
    public Mono<String> monoFailure() {
        return Mono.error(new IOException("BAM!"));
    }

    @Executable
    public Mono<String> monoFallback() {
        return Mono.just("Recovered");
    }

    @Executable
    public Flux<String> fluxFallback() {
        return Flux.just("Recovered");
    }
}
