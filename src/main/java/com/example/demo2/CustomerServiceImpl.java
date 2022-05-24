package com.example.demo2;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Function<UUID, CustomerDto> getCustomer() {
        return customerUuid -> customerMapper.customerToDto(customerRepository.findById(customerUuid).orElseThrow(() -> new RuntimeException("customer Not found")));
    }

    //potential pagination
    @Override
    public Supplier<List<CustomerDto>> getAllCustomerDto() {
        return () -> StreamSupport.stream(customerRepository.findAll().spliterator(), false)//
                .map(customerMapper::customerToDto).toList();
    }

    public Function<CustomerCreateDto, CustomerDto> createCustomer() {
        return customerCreateDto -> {
            Assert.notNull(customerCreateDto, "customerCreateDto can not be null");
            Assert.notNull(customerCreateDto.getCustomerName(), "customerName can not be null");
            Assert.notNull(customerCreateDto.getCity(), "city can not be null");
            return customerMapper.customerToDto(customerRepository.save(CustomerEntity.builder().customerName(customerCreateDto.getCustomerName()).build()));
        };
    }

    public String greeting(String name) {
        Assert.notNull(name, "Name can not be null");
        return String.format("Hello %s!", name);
    }
}
