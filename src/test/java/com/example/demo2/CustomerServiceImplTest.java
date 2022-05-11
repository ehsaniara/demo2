package com.example.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void testCreateCustomer() {
        var customerEntity = CustomerEntity.builder().customerId(1L).customerName("Jay").build();

        var customerDto = CustomerDto.builder()//
                .customerId(customerEntity.getCustomerId())//
                .customerName(customerEntity.getCustomerName())//
                .build();

        doReturn(customerEntity).when(customerRepository).save(any());

        doReturn(customerDto).when(customerMapper).customerToDto(any());

        var customerCreateDto = CustomerCreateDto.builder()//
                .customerName(customerEntity.getCustomerName())//
                .build();

        var result = customerService.createCustomer(customerCreateDto);
        Assertions.assertNotNull(result);

        var resultDto = customerMapper.customerToDto(customerEntity);
        Assertions.assertNotNull(resultDto);

        Assertions.assertEquals(customerDto.getCustomerName(), resultDto.getCustomerName());
        Assertions.assertEquals(customerDto.getCustomerId(), resultDto.getCustomerId());
    }

    @Test
    void testCreateCustomer_NullObject() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.createCustomer(null),
                "IllegalArgumentException was expected when object is null"
        );
        assertTrue(thrown.getMessage().contains("customerCreateDto can not be null"));
    }

    @Test
    void testCreateCustomer_NullName() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.createCustomer(CustomerCreateDto.builder().build()),
                "IllegalArgumentException was expected when name is null"
        );
        assertTrue(thrown.getMessage().contains("customerName can not be null"));
    }

    @Test
    void testGreeting() {
        var result = customerService.greeting("Jay");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hello Jay!", result);
    }

    @Test
    void testGreeting_NullName() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.greeting(null),
                "IllegalArgumentException was expected when name is null"
        );
        assertTrue(thrown.getMessage().contains("Name can not be null"));
    }
}
