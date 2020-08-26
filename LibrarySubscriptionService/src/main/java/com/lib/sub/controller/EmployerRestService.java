package com.lib.sub.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmployerRestService {

	@Value("${spring.application.name}")
	String appName;

	@Value("${server.port}")
	String portNumber;

	 @Autowired
	
	// @Lazy()
	  RestTemplate restTemplate;
	 

	@GetMapping("/hello")

	public String getHello() {
		return "hello";

	}

	@RequestMapping(value = "/Employer/get/{id}", method = RequestMethod.POST, consumes = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, produces = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")

	public String getEmployee(@PathVariable String id) {

		StringBuilder message = new StringBuilder();
		message.append("AppName: " + appName + ", \n");
		message.append("portNumber: " + portNumber + ", \n");
		// message.append("inside store calling product service: " +
		// callWithServiceDiscovery(id) + ",
		// \n"+"Storeserice"+callWithStoreServiceDiscovery(id));
		// message.append("Product: " + httpClientLb(id) + ", \n");
		message.append("Employee" + callEmployeeServiceDiscovery(id));

		return message.toString();

	}

/*	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		httpRequestFactory.setConnectionRequestTimeout(30000);
		httpRequestFactory.setConnectTimeout(30000);
		httpRequestFactory.setReadTimeout(30000);
		return new RestTemplate(httpRequestFactory);

	}*/
	/*
	 * @Bean
	 * 
	 * @LoadBalanced public RestTemplate restTemplate() {
	 * 
	 * HttpComponentsClientHttpRequestFactory httpRequestFactory = new
	 * HttpComponentsClientHttpRequestFactory();
	 * 
	 * httpRequestFactory.setConnectionRequestTimeout(30000);
	 * httpRequestFactory.setConnectTimeout(30000);
	 * httpRequestFactory.setReadTimeout(30000); return new
	 * RestTemplate(httpRequestFactory);
	 * 
	 * }
	 */

	private String callEmployeeServiceDiscovery(String id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		// String uri = "http://EmployeeService/Employee/get/" + id;
		String uri = "http://librarybs/bookservice/hello";
		///bookservice/books/updateAvailability/B1213/-1
		uri = "http://librarybs/bookservice/books/updateAvailability/B1213/1";

		System.out.println("URI:" + uri);

		String upc = restTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
		}, id).getBody();
		return upc;

	}

}
