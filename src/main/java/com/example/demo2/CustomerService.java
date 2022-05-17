package com.example.demo2;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomer(UUID customerUuid);
    List<CustomerDto> getAllCustomerDto();
    CustomerDto createCustomer(CustomerCreateDto customerCreateDto);
    String greeting(String name);
}
