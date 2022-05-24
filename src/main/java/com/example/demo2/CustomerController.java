package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.createCustomer().apply(customerCreateDto);
    }

    @GetMapping("/{customerUuid}")
    public CustomerDto getCustomerDto(@PathVariable(name = "customerUuid") UUID customerUuid) {
        return customerService.getCustomer().apply(customerUuid);
    }

    @GetMapping("/")
    public List<CustomerDto> getAllCustomerDto() {
        return customerService.getAllCustomerDto().get();
    }
}
