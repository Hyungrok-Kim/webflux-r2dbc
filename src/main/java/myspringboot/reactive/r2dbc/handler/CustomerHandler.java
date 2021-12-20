package myspringboot.reactive.r2dbc.handler;

import lombok.RequiredArgsConstructor;
import myspringboot.reactive.r2dbc.dao.CustomerDao;
import myspringboot.reactive.r2dbc.dto.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerHandler {
    private final CustomerDao dao;

    public Mono<ServerResponse> loadCustomersStream(ServerRequest serverRequest) {
        Flux<Customer> customerFlux = dao.getCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest serverRequest){
        int customerId = Integer.valueOf(serverRequest.pathVariable("id"));
        Mono<Customer> customerMono = dao.getCustomersStreamNoDelay()
                .filter(customer -> customer.getId() == customerId)
                .next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest) {
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> stringMono = customerMono.map(customer -> customer.getId() + " : " + customer.getName());
        return ServerResponse.ok().body(stringMono, String.class);
    }
}
