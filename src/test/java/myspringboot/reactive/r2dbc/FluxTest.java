package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {
    @Test
    public void justFlux() {
        Flux<String> stringFlux = Flux.just("Hello", "WebFlux").log();
        stringFlux.subscribe(val -> System.out.println("val = " + val));

        // StepVerifier 사용
        StepVerifier.create(stringFlux)
                    .expectNext("Hello")
                    .expectNext("WebFlux")
//                    .expectNextCount(2)
                    .verifyComplete();
//                    .expectComplete()
//                    .verify();
    }

    @Test
    public void errorFlux() {
        Flux<String> flux = Flux.just("Boot", "MSA")
                .concatWithValues("Cloud")
                .concatWith(Flux.error(new RuntimeException("Exception 발생")))
                .concatWithValues("Reactive Mongo")
                .log();

        flux.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));

    }
}
