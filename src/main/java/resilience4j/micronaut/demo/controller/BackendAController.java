package resilience4j.micronaut.demo.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import resilience4j.micronaut.demo.service.Service;

import javax.inject.Inject;
import javax.inject.Named;

@Controller("/backendA")
public class BackendAController {

    private final Service businessAService;

    @Inject
    public BackendAController(@Named("businessAService") Service businessAService) {
        this.businessAService = businessAService;
    }

    @Get(value = "monoSuccess", produces = MediaType.TEXT_PLAIN)
    public Mono<String> monoSuccess() {
        return businessAService.monoSuccess();
    }

    @Get(value = "monoFailure", produces = MediaType.TEXT_PLAIN)
    public Mono<String> monoFailure() {
        return businessAService.monoFailure();
    }

    @Get(value = "fluxSuccess", produces = MediaType.TEXT_PLAIN)
    public Flux<String> fluxSuccess() {
        return businessAService.fluxSuccess();
    }

    @Get(value = "fluxFailure", produces = MediaType.TEXT_PLAIN)
    public Flux<String> fluxFailure() {
        return businessAService.fluxFailure();
    }

}
