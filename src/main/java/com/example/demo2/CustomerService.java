package com.example.demo2;

import org.apache.logging.log4j.util.Supplier;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public interface CustomerService {

    Function<UUID, CustomerDto> getCustomer();

    Supplier<List<CustomerDto>> getAllCustomerDto();

    Function<CustomerCreateDto, CustomerDto> createCustomer();

    String greeting(String name);
}
