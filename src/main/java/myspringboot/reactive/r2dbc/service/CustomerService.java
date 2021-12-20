package myspringboot.reactive.r2dbc.service;

import lombok.RequiredArgsConstructor;
import myspringboot.reactive.r2dbc.dao.CustomerDao;
import myspringboot.reactive.r2dbc.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;

    public List<Customer> loadAllCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customerList = customerDao.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Total Execution Time = " + (end - start) + "ms");
        return customerList;
    }

    public Flux<Customer> loadAllCustomersStream () {
        long start = System.currentTimeMillis();
        return customerDao.getCustomersStream()
                    .doFinally(val -> System.out.println("Total Execution Time = " +
                            (System.currentTimeMillis() - start) + "ms"));
    }
}