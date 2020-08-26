package com.lib.service;

import com.lib.model.Book;

public interface BookService {

	public Iterable<Book> getBooks();

	public Book getBookById(String id);

	public Book save(Book book);

	public void delete(String id);
}
