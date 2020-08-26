package com.lib.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lib.jpa.BookRepository;
import com.lib.model.Book;

import ch.qos.logback.classic.Logger;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	private static final Logger log = (Logger) LoggerFactory.getLogger(BookServiceImpl.class.getName());

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Iterable<Book> getBooks() {
		log.info("BookServiceImpl - getBooks() - Total Books available=" + bookRepository.findAll().size());
		return bookRepository.findAll();

	}

	public Book getBookById(String bookId) {

		log.info("BookServiceImpl - getBookById() - Id=" + bookId);
		try {
			return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
		} catch (BookNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public Book save(Book book) {
		log.info("Book Service Impl - save" + book.getBook_id());

		return bookRepository.save(book);
	}

	public void delete(String id) {
		log.info("Book Service Impl - delete" + id);

		bookRepository.deleteById(id);
	}

}
