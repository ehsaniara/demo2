package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{name}")
    public String sayHi(@PathVariable(name = "name") String name) {
        return customerService.greeting(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.createCustomer(customerCreateDto);
    }
}
