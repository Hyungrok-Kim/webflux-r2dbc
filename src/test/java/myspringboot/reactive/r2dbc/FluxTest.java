package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
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

        StepVerifier.create(flux)
                    .expectNext("Boot")
                    .expectNext("MSA")
                    .expectNext("Cloud")
                    .expectError(RuntimeException.class)
                    .verify();
    }

    @Test
    public void subscriberFlux() {
        Flux<String> stringFlux = Flux.just("Hello", "WebFlux", "Boot"); //.log();
        stringFlux.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Integer.MAX_VALUE);
                // s.request(2);
            }

            @Override
            public void onNext(String s) {
                System.out.println("FluxTest.onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("FluxTest.onError " + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("FluxTest.onComplete");
            }
        });

        StepVerifier.create(stringFlux)
                    .expectNext("Hello")
                    .expectNext("WebFlux")
                    .expectNext("Boot")
                    .expectComplete();
    }

    @Test
    public void rangeFlux() {
        Flux<Integer> integerFlux = Flux.range(10, 10)
                .filter(num -> Math.floorMod(num, 2) == 1)
                .log();

        integerFlux.subscribe(System.out::println);

        StepVerifier.create(integerFlux)
                    .expectNext(11, 13, 15, 17, 19)
                    .verifyComplete();
    }
}
