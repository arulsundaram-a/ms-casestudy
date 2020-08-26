package com.lib.service;

public class BookNotFoundException extends Exception {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String book_id) {
		super(String.format("Book is not found with id : '%s'", book_id));
	}
}