package com.lib.sub.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "subscription")
public class Subscription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Subscription() {

	}

	Subscription(String subscription_name, String book_id, Date date_subscribed, Date date_returned,String notify) {
		this.subscription_name = subscription_name;
		this.book_id = book_id;
		this.date_subscribed = date_subscribed;
		this.date_returned = date_returned;
		this.notify=notify;
	}

	
	@Id
	@NotBlank
	
	private String book_id;
	@NotBlank
	private String subscription_name;
	private Date date_subscribed;
	private Date date_returned;
	private String notify="no";

	public String getSubscription_name() {
		return subscription_name;
	}

	public void setSubscription_name(String subscription_name) {
		this.subscription_name = subscription_name;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public Date getDate_subscribed() {
		return date_subscribed;
	}

	public void setDate_subscribed(Date date_subscribed) {
		this.date_subscribed = date_subscribed;
	}

	public Date getDate_returned() {
		return date_returned;
	}

	public void setDate_returned(Date date_returned) {
		this.date_returned = date_returned;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

}
