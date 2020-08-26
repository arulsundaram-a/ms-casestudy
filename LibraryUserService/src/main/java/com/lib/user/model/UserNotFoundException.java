package com.lib.user.model;

public class UserNotFoundException extends Exception {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String book_id) {
		super(String.format("Book is not found with id : '%s'", book_id));
	}
}