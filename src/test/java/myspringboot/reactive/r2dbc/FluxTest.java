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
        StepVerifier.create(stringFlux);
    }
}
