package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerEntity createCustomer(String name) {
        return customerRepository.save(CustomerEntity.builder().customerName(name).build());
    }

    public String greeting(String name) {
        return String.format("Hello %s!", name);
    }
}
