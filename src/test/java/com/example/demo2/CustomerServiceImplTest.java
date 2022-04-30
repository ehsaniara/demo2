package com.example.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void testCreateCustomer() {
        var customerEntity = CustomerEntity.builder().customerId(1L).customerName("Jay").build();
        doReturn(customerEntity).when(customerRepository).save(any());

        var result = customerService.createCustomer("Jay");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getCustomerId());
        Assertions.assertEquals("Jay", result.getCustomerName());
    }

    @Test
    void testGreeting() {
        var result = customerService.greeting("Jay");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hello Jay!", result);
    }
}