package com.example.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
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
    void testSayHi() throws Exception {

        var customerName = "Jay";

        when(customerService.greeting(customerName)).thenReturn("Hi Jay!");

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/{name}", customerName)).andExpect(MockMvcResultMatchers.status().isOk())//
                .andDo(print())//
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.equalTo("Hi Jay!")));
    }

    @Test
    void testCreateCustomer() throws Exception {

        var customerName = "Jay";
        var customerId = 1L;
        var customerDto = CustomerDto.builder()//
                .customerId(customerId)//
                .customerName(customerName)//
                .build();
        var customerCreateDto = CustomerCreateDto.builder().customerName(customerName).build();

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

        perform
                .andDo(print())
                //
                //
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(customerId))
                //
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(customerName));

    }
}
