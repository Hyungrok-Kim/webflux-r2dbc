package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class CombineFlux {
    //merge와 concat
    @Test
    public void combineUsingMerge() {
        Flux<String> lastNames = Flux.just("김", "박", "고");
        Flux<String> firstNames = Flux.just("영희", "둘리", "길동");

        Flux<String> names = Flux.merge(lastNames, firstNames).log();

        names.subscribe(System.out::println);

        StepVerifier.create(names)
                    .expectNext("김","박","고","영희","둘리","길동")
                    .verifyComplete();
    }

    @Test
    public void combineUsingMergeDelay() {
        Flux<String> lastNames = Flux.just("김", "박", "고").delayElements(Duration.ofSeconds(1));
        Flux<String> firstNames = Flux.just("영희", "둘리", "길동").delayElements((Duration.ofSeconds(1)));

        Flux<String> names = Flux.merge(lastNames, firstNames).log();

        names.subscribe(System.out::println, System.out::println);

        StepVerifier.create(names)
                    .expectNextCount(6)
                    .verifyComplete();
    }

    @Test
    public void combineUsingConcatDelay() {
        Flux<String> lastNames = Flux.just("김", "박", "고").delayElements(Duration.ofSeconds(1));
        Flux<String> firstNames = Flux.just("영희", "둘리", "길동").delayElements((Duration.ofSeconds(1)));

        Flux<String> names = Flux.concat(lastNames, firstNames).log();

        names.subscribe(System.out::println, System.out::println);

        StepVerifier.create(names)
                .expectNextCount(6)
                .verifyComplete();
    }

}
