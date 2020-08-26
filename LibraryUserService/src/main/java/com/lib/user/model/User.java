package com.lib.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "user")
public class User {
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String subscriber_name;
	private String password;
	private String email;

	User() {

	}

	User(String id, String subscriber_name, String password, String email) {
		super();
		this.id = id;
		this.subscriber_name = subscriber_name;
		this.password = password;
		this.email = email;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSubscriber_name() {
		return subscriber_name;
	}
	public void setSubscriber_name(String subscriber_name) {
		this.subscriber_name = subscriber_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
