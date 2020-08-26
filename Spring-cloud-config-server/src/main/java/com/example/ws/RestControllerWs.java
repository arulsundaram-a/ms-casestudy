package com.example.ws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerWs {
	@GetMapping("/greetings_server")
	public String greeting() {
		
		return "hello";
	}
}
