package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto createCustomer(CustomerCreateDto customerCreateDto) {
        return customerMapper.customerToDto(customerRepository.save(CustomerEntity.builder().customerName(customerCreateDto.getCustomerName()).build()));
    }

    public String greeting(String name) {
        return String.format("Hello %s!", name);
    }
}
