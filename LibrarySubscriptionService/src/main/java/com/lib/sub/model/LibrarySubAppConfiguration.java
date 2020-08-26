package com.lib.sub.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("booksubscriptionservice")
public class LibrarySubAppConfiguration {

	String message;
	String name;
	String indicator;
	String book_service_path;
	String book_service_euraka_path;
	


	public String getBook_service_euraka_path() {
		return book_service_euraka_path;
	}

	public void setBook_service_euraka_path(String book_service_euraka_path) {
		this.book_service_euraka_path = book_service_euraka_path;
	}

	public String getBook_service_path() {
		return book_service_path;
	}

	public void setBook_service_path(String book_service_path) {
		this.book_service_path = book_service_path;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
}
