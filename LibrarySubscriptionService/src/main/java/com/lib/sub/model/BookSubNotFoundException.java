package com.lib.sub.model;

public class BookSubNotFoundException extends Exception {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public BookSubNotFoundException(String book_id) {
		super(String.format("Book is not found with id : '%s'", book_id));
	}
}