package com.example.demo2;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSayHi() throws Exception {

        Mockito.when(customerService.greeting("Jay")).thenReturn("Hi Jay!");

        mockMvc.perform(MockMvcRequestBuilders.get("/Jay")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(CoreMatchers.equalTo("Hi Jay!")));
    }

    @Test
    void testCreateCustomer() throws Exception {
        var customerEntity = CustomerEntity.builder().customerId(1L).customerName("Jay").build();
        Mockito.when(customerService.createCustomer("Jay")).thenReturn(customerEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/Jay"))
                //
                .andExpect(MockMvcResultMatchers.status().isOk())
                // match for customerId = 1
                .andExpect(jsonPath("customerId", Matchers.is(1)))
                // match for customerName = Jay
                .andExpect(jsonPath("customerName", Matchers.is("Jay")));
    }
}