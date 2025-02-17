package com.ewolff.microservice.customer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerWebIntegrationTest {

	@Autowired
	private CustomerRepository customerRepository;

	@LocalServerPort
	private int serverPort;

	private RestTemplate restTemplate;

	private <T> T getForMediaType(Class<T> value, MediaType mediaType, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(mediaType));

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<T> resultEntity = restTemplate.exchange(url, HttpMethod.GET, entity, value);

		return resultEntity.getBody();
	}

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}

	private String customerURL() {
		return "http://localhost:" + serverPort + "/";
	}

	@Test
	public void IsCustomerFormDisplayed() {
		ResponseEntity<String> resultEntity = restTemplate.getForEntity(customerURL() + "/form.html", String.class);
		assertTrue(resultEntity.getStatusCode().is2xxSuccessful());
		assertTrue(resultEntity.getBody().contains("<form"));
	}

	@Test
	@Transactional
	public void IsSubmittedCustomerSaved() {
		assertEquals(0, customerRepository.findByName("Hoeller").size());
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("firstname", "Juergen");
		map.add("name", "Hoeller");
		map.add("street", "Schlossallee");
		map.add("city", "Linz");
		map.add("email", "springjuergen@twitter.com");

		restTemplate.postForObject(customerURL() + "form.html", map, String.class);
		assertEquals(1, customerRepository.findByName("Hoeller").size());
	}

}
