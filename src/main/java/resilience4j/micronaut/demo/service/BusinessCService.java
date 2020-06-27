package resilience4j.micronaut.demo.service;

import io.github.resilience4j.annotation.Bulkhead;
import io.github.resilience4j.annotation.CircuitBreaker;
import io.github.resilience4j.annotation.Retry;
import io.github.resilience4j.annotation.TimeLimiter;
import io.micronaut.context.annotation.Executable;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vavr.control.Try;
import resilience4j.micronaut.demo.exception.BusinessException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton()
@Named("businessCService")
public class BusinessCService implements Service {

    private static final String BACKEND_C = "backendC";

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public String failure() {
        throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
    }

    @Override
    @CircuitBreaker(name = BACKEND_C, fallbackMethod = "fallback")
    public String failureWithFallback() {
        throw new BusinessException("This exception is ignored by the CircuitBreaker of backend A");
    }

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public String success() {
        return "Hello World from backend A";
    }

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    public String successException() {
        throw new HttpStatusException(HttpStatus.BAD_REQUEST, "This is a remote client exception");
    }

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    public String ignoreException() {
        throw new BusinessException("This exception is ignored by the CircuitBreaker of backend A");
    }

    @Override
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public Flowable<String> fluxSuccess() {
        return Flowable.just("Hello", "World");
    }

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public Flowable<String> fluxFailure() {
        return Flowable.error(new IOException("BAM!"));
    }

    @Override
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C, fallbackMethod = "fluxFallback")
    public Flowable<String> fluxTimeout() {
        return Flowable.just("Hello World from backend A").delay(10, TimeUnit.SECONDS);
    }

    @Override
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public Single<String> monoSuccess() {
        return Single.just("Hello World from backend A");
    }

    @Override
    @CircuitBreaker(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public Single<String> monoFailure() {
        return Single.error(new IOException("BAM!"));
    }

    @Override
    @TimeLimiter(name = BACKEND_C)
    @Bulkhead(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C, fallbackMethod = "monoFallback")
    public Single<String> monoTimeout() {
        return Single.just("Hello World from backend A")
            .delay(10, TimeUnit.SECONDS);
    }

    @Override
    @Bulkhead(name = BACKEND_C, type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public CompletableFuture<String> futureSuccess() {
        return CompletableFuture.completedFuture("Hello World from backend A");
    }

    @Override
    @Bulkhead(name = BACKEND_C, type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C)
    @Retry(name = BACKEND_C)
    public CompletableFuture<String> futureFailure() {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new IOException("BAM!"));
        return future;
    }

    @Override
    @Bulkhead(name = BACKEND_C, type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = BACKEND_C)
    @CircuitBreaker(name = BACKEND_C, fallbackMethod = "futureFallback")
    public CompletableFuture<String> futureTimeout() {
        Try.run(() -> Thread.sleep(5000));
        return CompletableFuture.completedFuture("Hello World from backend A");
    }

    @Executable
    public String fallback() {
        return "Recovered HttpServerErrorException";
    }

    @Executable
    public CompletableFuture<String> futureFallback() {
        return CompletableFuture.completedFuture("Recovered specific TimeoutException");
    }

    @Executable
    public Single<String> monoFallback() {
        return Single.just("Recovered");
    }

    @Executable
    public Flowable<String> fluxFallback() {
        return Flowable.just("Recovered");
    }
}
