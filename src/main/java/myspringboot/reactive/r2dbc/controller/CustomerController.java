package myspringboot.reactive.r2dbc.controller;

import lombok.RequiredArgsConstructor;
import myspringboot.reactive.r2dbc.dto.Customer;
import myspringboot.reactive.r2dbc.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.loadAllCustomers();
    }
}