package com.example.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

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
    void testGreeting() {
        var result = customerService.greeting("Jay");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hello Jay!", result);
    }
}
