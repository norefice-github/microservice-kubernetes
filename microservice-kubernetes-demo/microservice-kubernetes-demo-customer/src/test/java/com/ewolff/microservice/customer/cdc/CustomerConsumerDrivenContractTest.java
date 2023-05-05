package com.ewolff.microservice.customer.cdc;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ewolff.microservice.customer.CustomerApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
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
