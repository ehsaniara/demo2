package com.example.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCustomer() throws Exception {

        var customerName = "Jay";
        var city = "Los Angeles";
        var randomUuid = UUID.randomUUID();
        var customerDto = CustomerDto.builder()//
                .customerUuid(randomUuid)//
                .customerName(customerName)//
                .city(city)//
                .build();
        var customerCreateDto = CustomerCreateDto.builder().customerName(customerName).city(city).build();

        given(customerService.createCustomer(customerCreateDto)).willReturn(customerDto);

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/customer/")
                        //
                        .content(objectMapper.writeValueAsString(customerCreateDto))
                        //
                        .characterEncoding("utf-8")
                        //
                        .contentType(MediaType.APPLICATION_JSON)
                        //
                        .accept(MediaType.APPLICATION_JSON)
                //
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        verify(customerService, times(1)).createCustomer(customerCreateDto);

        perform.andDo(print())
                //note: for UUID we should see it as string in json payloads
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerUuid").value(randomUuid.toString()))
                //
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(customerName));
    }

    @Test
    void testCreateCustomer_nullName() throws Exception {
        //null check
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/").content(objectMapper.writeValueAsString(CustomerCreateDto.builder().build())).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.error").value(Matchers.containsString("customerName can not be null or blank")));

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/").content(objectMapper.writeValueAsString(CustomerCreateDto.builder().customerName("Jay").build())).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.error").value(Matchers.containsString("city can not be null or blank")));

        //black check
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/").content(objectMapper.writeValueAsString(CustomerCreateDto.builder().customerName("").build())).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.error").value(Matchers.containsString("customerName can not be null or blank")));

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/").content(objectMapper.writeValueAsString(CustomerCreateDto.builder().customerName("Jay").city("").build())).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.error").value(Matchers.containsString("city can not be null or blank")));
    }

    @Test
    void getCustomer() throws Exception {
        var customerDto = CustomerDto.builder().customerUuid(UUID.randomUUID()).customerName("Jay").build();

        when(customerService.getCustomer(customerDto.getCustomerUuid())).thenReturn(customerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/{customerUuid}", customerDto.getCustomerUuid())).andExpect(MockMvcResultMatchers.status().isOk())//
                .andDo(print())//
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.customerUuid").value(customerDto.getCustomerUuid().toString())).andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(customerDto.getCustomerName()));

    }

    @Test
    void testGetAllCustomerDto() throws Exception {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> customerDtoList.add(CustomerDto.builder().customerUuid(UUID.randomUUID()).customerName("Jay" + i).build()));

        when(customerService.getAllCustomerDto()).thenReturn(customerDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/")).andExpect(MockMvcResultMatchers.status().isOk())//
                .andDo(print())//
                .andExpect(MockMvcResultMatchers.status().isOk())//
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerUuid").value(customerDtoList.get(0).getCustomerUuid().toString()))//
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerName").value(customerDtoList.get(0).getCustomerName()));//

    }
}
