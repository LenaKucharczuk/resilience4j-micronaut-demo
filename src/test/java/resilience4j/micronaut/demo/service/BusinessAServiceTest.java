package resilience4j.micronaut.demo.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest()
class BusinessAServiceTest {

    @Inject
    @Named("businessAService")
    Service businessAService;

    @Test
    void fluxSuccess() {
        NoClassDefFoundError exception = assertThrows(NoClassDefFoundError.class, () ->
            businessAService.fluxSuccess()
        );
        assertEquals(exception.getMessage(),
                "io/github/resilience4j/ratelimiter/operator/RateLimiterOperator");
    }

    @Test
    void fluxFailure() {
        NoClassDefFoundError exception = assertThrows(NoClassDefFoundError.class, () ->
                businessAService.fluxFailure()
        );
        assertEquals(exception.getMessage(),
                "io/github/resilience4j/ratelimiter/operator/RateLimiterOperator");
    }

    @Test
    void monoSuccess() {
        NoClassDefFoundError exception = assertThrows(NoClassDefFoundError.class, () ->
                businessAService.monoSuccess()
        );
        assertEquals(exception.getMessage(),
                "io/github/resilience4j/ratelimiter/operator/RateLimiterOperator");
    }

    @Test
    void monoFailure() {
        NoClassDefFoundError exception = assertThrows(NoClassDefFoundError.class, () ->
                businessAService.monoFailure()
        );
        assertEquals(exception.getMessage(),
                "io/github/resilience4j/ratelimiter/operator/RateLimiterOperator");
    }
}