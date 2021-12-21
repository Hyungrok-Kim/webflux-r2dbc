package myspringboot.reactive.r2dbc.h2.controller;

import myspringboot.reactive.r2dbc.h2.entity.Customer;
import myspringboot.reactive.r2dbc.h2.repository.R2CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/r2customers")
public class R2CustomerController {
    private final R2CustomerRepository customerRepository;

    public R2CustomerController(R2CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> findAllCustomer() {
        return customerRepository.findAll().log();
    }

    @GetMapping("/{id}")
    public Mono<Customer> findCustomer(@PathVariable Long id) {
        return customerRepository.findById(id).log();
    }
}
