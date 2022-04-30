package com.example.demo2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {"DB_USERNAME = postgres", "DB_PASSWORD = postgres"})
class Demo2ApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
		assertThat(customerService).isNotNull();
	}

}
