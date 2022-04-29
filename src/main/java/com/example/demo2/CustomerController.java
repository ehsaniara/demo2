package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{name}")
    public String sayHi(@PathVariable(name = "name") String name) {
        return customerService.greeting(name);
    }

    @PostMapping("/{name}")
    public CustomerEntity createCustomer(@PathVariable(name = "name") String name) {
        return customerService.createCustomer(name);
    }
}
