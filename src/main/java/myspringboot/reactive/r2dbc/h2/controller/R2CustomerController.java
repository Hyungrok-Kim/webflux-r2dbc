package myspringboot.reactive.r2dbc.h2.controller;

import myspringboot.reactive.r2dbc.h2.entity.Customer;
import myspringboot.reactive.r2dbc.h2.repository.R2CustomerRepository;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/r2customers")
public class R2CustomerController {
    private final R2CustomerRepository customerRepository;
    /*
        조회요청 -> Flux
        등록요청 -> Flux
        Sinks는 Flux.merge로 두 개의 Flux를 합쳐주는 역할을 한다.
     */
    private final Sinks.Many<Customer> sinks;

    public R2CustomerController(R2CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        // Sinks.many() => Sinks.ManySpec이라는게 생성이 됨.
        // Sinks.many().multicast() => Sinks.MulticastSpec이라는게 생성이 됨.
        sinks = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> findAllCustomer() {
        return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    //ServerSentEvent 객체를 직접 사용하면 MediaType.TEXT_EVENT_STREAM_VALUE 설정을 생략할 수 있다.
    @GetMapping(value = "/sse")
    public Flux<ServerSentEvent<Customer>> findAllCustomerSSE() {
        return sinks.asFlux()
                    .mergeWith(customerRepository.findAll())
                    .map(customer -> ServerSentEvent.builder(customer).build());
    }

    @GetMapping("/{id}")
    public Mono<Customer> findCustomer(@PathVariable Long id) {
        return customerRepository.findById(id).log();
    }

    @PostMapping
    public Mono<Customer> saveCustomer(@RequestBody Customer customerMono) {
        //tryEmitNet : Try emitting a non-null elements, generating on onNext signal
        return customerRepository.save(customerMono).doOnNext(
                insCust -> sinks.tryEmitNext(insCust)
        ).log();
    }
}
