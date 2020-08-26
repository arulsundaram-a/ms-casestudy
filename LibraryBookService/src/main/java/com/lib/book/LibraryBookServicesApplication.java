package com.lib.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.lib.controller,com.lib.service")
@EntityScan("com.lib.model")
@EnableJpaRepositories("com.lib.jpa")
@SpringBootApplication
public class LibraryBookServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryBookServicesApplication.class, args);
	}

}
