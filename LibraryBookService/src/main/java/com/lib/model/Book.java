package com.lib.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")

public class Book {

	@Id
	private String book_id;

	@NotBlank
	private String book_name;
	@NotBlank
	private String author;
	@NotNull
	private int available_copies;
	@NotNull
	private int total_copies;

	Book()  {

	}


	Book(String book_id, String book_name, String author, int available_copies, int total_copies) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.author = author;
		this.available_copies = available_copies;
		this.total_copies = total_copies;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAvailable_copies() {
		return available_copies;
	}

	public void setAvailable_copies(int available_copies) {
		this.available_copies = available_copies;
	}

	public int getTotal_copies() {
		return total_copies;
	}

	public void setTotal_copies(int total_copies) {
		this.total_copies = total_copies;
	}

}
