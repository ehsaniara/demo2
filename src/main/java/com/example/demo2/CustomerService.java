package com.example.demo2;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomer(UUID customerUuid);
    CustomerDto createCustomer(CustomerCreateDto customerCreateDto);
    String greeting(String name);
}
