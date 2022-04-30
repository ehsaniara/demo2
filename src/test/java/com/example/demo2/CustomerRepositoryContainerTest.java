package com.example.demo2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryContainerTest {

    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12").withDatabaseName("springboot").withUsername("springboot").withPassword("springboot");

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.password", database::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCreateCustomer() {
        var customerEntity = CustomerEntity.builder().customerName("Jay").build();

        customerRepository.save(customerEntity);

        List<CustomerEntity> customers = new ArrayList<>();

        customerRepository.findAll().forEach(customers::add);

        Assertions.assertThat(customers).extracting(CustomerEntity::getCustomerName).containsOnly("Jay");
        Assertions.assertThat(customers).extracting(CustomerEntity::getCustomerId).containsOnly(1L);
    }
}
