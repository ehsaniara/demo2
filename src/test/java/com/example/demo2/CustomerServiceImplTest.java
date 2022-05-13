package com.example.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        var customerEntity = CustomerEntity.builder().customerUuid(UUID.randomUUID()).customerName("Jay").build();

        var customerDto = CustomerDto.builder()//
                .customerUuid(customerEntity.getCustomerUuid())//
                .customerName(customerEntity.getCustomerName())//
                .build();

        var customerCreateDto = CustomerCreateDto.builder()//
                .customerName(customerEntity.getCustomerName())//
                .build();

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        doReturn(customerDto).when(customerMapper).customerToDto(customerEntity);

        var result = customerService.createCustomer(customerCreateDto);
        Assertions.assertNotNull(result);

        verify(customerRepository, times(1)).save(any());

        //almost same as last verify
        ArgumentCaptor<CustomerEntity> personArgument = ArgumentCaptor.forClass(CustomerEntity.class);
        verify(customerRepository, times(1)).save(personArgument.capture());
        verifyNoMoreInteractions(customerRepository);

        //comparing the result with what it should be
        assertEquals(customerDto, result);
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
        assertEquals("Hello Jay!", result);
    }

    @Test
    void testGreeting_NullName() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> customerService.greeting(null), "IllegalArgumentException was expected when name is null");
        assertTrue(thrown.getMessage().contains("Name can not be null"));
    }

    @Test
    void testGetCustomer() {
        var customerEntity = CustomerEntity.builder().customerUuid(UUID.randomUUID()).customerName("Jay").build();

        var customerDto = CustomerDto.builder()//
                .customerUuid(customerEntity.getCustomerUuid())//
                .customerName(customerEntity.getCustomerName())//
                .build();

        when(customerRepository.findById(customerEntity.getCustomerUuid())).thenReturn(Optional.of(customerEntity));

        doReturn(customerDto).when(customerMapper).customerToDto(customerEntity);

        var returned = customerService.getCustomer(customerEntity.getCustomerUuid());
        Assertions.assertNotNull(returned);

        verify(customerRepository, times(1)).findById(customerEntity.getCustomerUuid());
        verifyNoMoreInteractions(customerRepository);

        assertEquals(customerDto, returned);
    }

    @Test
    void testGetCustomer_NotFound() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> customerService.getCustomer(UUID.randomUUID()), "RuntimeException was expected when customer not found");
        assertTrue(thrown.getMessage().contains("customer Not found"));
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
