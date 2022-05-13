package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/name/{name}")
    public String sayHi(@PathVariable(name = "name") String name) {
        return customerService.greeting(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.createCustomer(customerCreateDto);
    }

    @GetMapping("/{customerUuid}")
    public CustomerDto getCustomerDto(@PathVariable(name = "customerUuid") UUID customerUuid) {
        return customerService.getCustomer(customerUuid);
    }
}
