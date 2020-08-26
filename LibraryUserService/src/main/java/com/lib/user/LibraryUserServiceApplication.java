package com.lib.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.lib.user.controller,com.lib.user.model")
@EntityScan("com.lib.user.model")
@EnableJpaRepositories("com.lib.user.jpa")
@SpringBootApplication
public class LibraryUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryUserServiceApplication.class, args);
	}

}
