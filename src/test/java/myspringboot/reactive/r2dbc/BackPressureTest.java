package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class BackPressureTest {
    @Test
    public void thenRequestTest() {
        Flux<Integer> flux = Flux.just(101, 201, 301).log();

        StepVerifier.create(flux)
                .thenRequest(1)
                .expectNext(101)
                .thenRequest(2)
                .expectNext(201,301)
                .verifyComplete();
    }
    
    @Test
    public void subscriptionTest() {
        Flux<Integer> flux = Flux.range(1, 100);

        flux.subscribe(
                value -> System.out.println("OnNext = " + value),
                error -> error.printStackTrace(),
                () -> System.out.println("OnComplete"),
                subscription -> subscription.request(10)
        );

        StepVerifier.create(flux)
                    .expectNextCount(10)
                    .expectComplete();
    }
}
