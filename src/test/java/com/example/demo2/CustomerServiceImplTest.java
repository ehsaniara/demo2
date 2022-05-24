package com.example.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

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

        var customerEntity = CustomerEntity.builder().customerUuid(UUID.randomUUID()).customerName("Jay").city("Los Angeles").build();

        var customerDto = CustomerDto.builder()//
                .customerUuid(customerEntity.getCustomerUuid())//
                .customerName(customerEntity.getCustomerName())//
                .customerName(customerEntity.getCity())//
                .build();

        var customerCreateDto = CustomerCreateDto.builder()//
                .customerName(customerEntity.getCustomerName())//
                .city(customerEntity.getCity())//
                .build();

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        doReturn(customerDto).when(customerMapper).customerToDto(customerEntity);

        var result = customerService.createCustomer().apply(customerCreateDto);
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
        var thrown = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer().apply(null), "IllegalArgumentException was expected when object is null");
        assertTrue(thrown.getMessage().contains("customerCreateDto can not be null"));
    }

    @Test
    void testCreateCustomer_NullName() {
        {
            var thrown = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer().apply(CustomerCreateDto.builder().build()), "IllegalArgumentException was expected when name is null");
            assertTrue(thrown.getMessage().contains("customerName can not be null"));
        }
        {
            var thrown = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer().apply(CustomerCreateDto.builder().customerName("Jay").build()), "IllegalArgumentException was expected when city is null");
            assertTrue(thrown.getMessage().contains("city can not be null"));
        }
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

        var returned = customerService.getCustomer().apply(customerEntity.getCustomerUuid());
        Assertions.assertNotNull(returned);

        verify(customerRepository, times(1)).findById(customerEntity.getCustomerUuid());
        verifyNoMoreInteractions(customerRepository);

        assertEquals(customerDto, returned);
    }

    @Test
    void testGetCustomer_NotFound() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> customerService.getCustomer().apply(UUID.randomUUID()), "RuntimeException was expected when customer not found");
        assertTrue(thrown.getMessage().contains("customer Not found"));
    }

    @Test
    void testGetAllCustomerDto() {

        List<CustomerEntity> customerEntityList = new ArrayList<>();

        IntStream.range(0, 3).forEach(i -> customerEntityList.add(CustomerEntity.builder().customerUuid(UUID.randomUUID()).customerName("Jay" + i).build()));

        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerEntityList.forEach(customerEntity -> customerDtoList.add(CustomerDto.builder()//
                .customerUuid(customerEntity.getCustomerUuid())//
                .customerName(customerEntity.getCustomerName())//
                .build()));

        when(customerRepository.findAll()).thenReturn(customerEntityList);

        IntStream.range(0, 3)//
                .forEach(i -> //
                        doReturn(customerDtoList.get(i)).when(customerMapper).customerToDto(customerEntityList.get(i)//
                        )//
                );

        var returned = customerService.getAllCustomerDto().get();
        Assertions.assertNotNull(returned);

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);

        assertEquals(customerDtoList, returned);
    }
}
