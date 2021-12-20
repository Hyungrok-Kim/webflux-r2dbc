package myspringboot.reactive.r2dbc.controller;

import myspringboot.reactive.r2dbc.dto.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerControllerWebTestClient {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getCustomerStream() {
        Flux<Customer> customerFlux = webTestClient.get()
                .uri("/customers/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk() // 200
                .returnResult(Customer.class)
                .getResponseBody()
                .log();

        StepVerifier.create(customerFlux)
                    .expectNextCount(10)
                    .verifyComplete();
    }
}
