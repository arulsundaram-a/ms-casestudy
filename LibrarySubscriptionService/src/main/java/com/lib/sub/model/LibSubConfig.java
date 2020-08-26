package com.lib.sub.model;

public class LibSubConfig {

	private String message = "";
	private String name = "";
	private String indicator;
	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public LibSubConfig(String message, String name) {
		this.message = message;
		this.name = name;
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

}
