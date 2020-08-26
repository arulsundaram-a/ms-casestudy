package com.lib.booksubscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.lib.sub.controller,com.lib.sub.model,com.lib.sub.jpa,com.lib.config")
@EntityScan("com.lib.sub.model")
@EnableJpaRepositories("com.lib.sub.jpa")

@SpringBootApplication
public class LibrarySubscriptionServiceApplication {

	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		httpRequestFactory.setConnectionRequestTimeout(30000);
		httpRequestFactory.setConnectTimeout(30000);
		httpRequestFactory.setReadTimeout(30000);
		return new RestTemplate(httpRequestFactory);

	}
	
	public static void main(String[] args) {
		SpringApplication.run(LibrarySubscriptionServiceApplication.class, args);
	}

}
