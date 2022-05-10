package com.example.demo2;

public interface CustomerService {

    CustomerDto createCustomer(CustomerCreateDto customerCreateDto);

    String greeting(String name);
}
