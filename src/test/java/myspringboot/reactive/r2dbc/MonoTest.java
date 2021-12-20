package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
    @Test
    public void justMono() {
        Mono<String> stringMono = Mono.just("Welcome to Webflux")
                .map(msg -> msg.concat(".com")).log();

        stringMono.subscribe(System.out::println);

        StepVerifier.create(stringMono)
                    .expectNext("Welcome to Webflux.com")
                    .verifyComplete();
    }

    @Test
    public void errorMono() {
        Mono<String> errorMono = Mono.error(new RuntimeException("Check Error Mono"));

        errorMono.subscribe(
                value -> {
                    System.out.println("onNext " + value);
                },
                error -> {
                    System.out.println("onError " + error.getMessage());
                },
                () -> {
                    System.out.println("onComplete ");
                }
        );

        StepVerifier.create(errorMono)
                    .expectError(RuntimeException.class)
                    .verify();
    }

    @Test
    public void fromSupplier() {
        //Supplier<String> StrSupplier = () -> "Supplier Message";
        Mono<String> stringMono = Mono.fromSupplier(() -> "Supplier Message").log();
        stringMono.subscribe(System.out::println);

        StepVerifier.create(stringMono)
                .expectNext("Supplier Message")
                .verifyComplete();
    }
}
