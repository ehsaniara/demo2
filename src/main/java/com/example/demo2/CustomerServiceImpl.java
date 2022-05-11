package com.example.demo2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto createCustomer(CustomerCreateDto customerCreateDto) {
        Assert.notNull(customerCreateDto, "customerCreateDto can not be null");
        Assert.notNull(customerCreateDto.getCustomerName(), "customerName can not be null");
        return customerMapper.customerToDto(customerRepository.save(CustomerEntity.builder().customerName(customerCreateDto.getCustomerName()).build()));
    }

    public String greeting(String name) {
        Assert.notNull(name, "Name can not be null");
        return String.format("Hello %s!", name);
    }
}
