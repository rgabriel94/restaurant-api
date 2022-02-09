package com.gabriel.restaurant.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private ModelMapper modelMapper;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(modelMapper);
	}

}
