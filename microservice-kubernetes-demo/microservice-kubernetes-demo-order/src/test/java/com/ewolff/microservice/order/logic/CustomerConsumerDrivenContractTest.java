package com.ewolff.microservice.order.logic;

import com.ewolff.microservice.order.OrderApp;
import com.ewolff.microservice.order.clients.Customer;
import com.ewolff.microservice.order.clients.CustomerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = OrderApp.class)
@ActiveProfiles("test")
public class CustomerConsumerDrivenContractTest {

	@Autowired
	CustomerClient customerClient;

	@Test
	public void testGetOne() {
		Collection<Customer> allCustomer = customerClient.findAll();
		Long id = allCustomer.iterator().next().getCustomerId();
		Customer result = customerClient.getOne(id);
		assertEquals(id.longValue(), result.getCustomerId());
	}

	@Test
	public void testValidCustomerId() {
		Collection<Customer> allCustomer = customerClient.findAll();
		Long id = allCustomer.iterator().next().getCustomerId();
		assertTrue(customerClient.isValidCustomerId(id));
		assertFalse(customerClient.isValidCustomerId(-1));
	}

}
