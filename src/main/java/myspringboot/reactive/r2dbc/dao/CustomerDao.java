
package myspringboot.reactive.r2dbc.dao;

import myspringboot.reactive.r2dbc.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {
    private static void sleepExecution(int i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1, 10)
                //.peek(i -> sleepExecution(i))
                .peek(CustomerDao::sleepExecution)
                .peek(i -> System.out.println("processing count : " + i))
                .mapToObj(i -> new Customer(i, "Customer" + i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1, 10)
                    .delayElements(Duration.ofSeconds(1))
                    .doOnNext(i -> System.out.println("Flux processing count : " + i))
                    .map(i -> new Customer(i, "Customer" + i));
    }
}