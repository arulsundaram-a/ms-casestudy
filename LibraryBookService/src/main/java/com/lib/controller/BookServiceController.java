package com.lib.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lib.model.Book;
import com.lib.service.BookService;
import com.lib.service.BookServiceImpl;

import ch.qos.logback.classic.Logger;
import com.lib.controller.Producer;
import com.lib.controller.Consumer;

@RestController

@RequestMapping("bookservice")
public class BookServiceController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(BookServiceImpl.class.getName());

	@Autowired
	private BookService bookService;
	
	
	@Autowired
	private final Producer producer;

	@Autowired
	private final Consumer consumer;

	
	public BookServiceController(Producer producer,Consumer consumer) {
		this.producer = producer;
		this.consumer=consumer;
	}
	@Autowired


/*	@Autowired
	public BookServiceController(Producer producer) {
		this.producer = producer;
	}
*/	@RequestMapping("/hello")
	public String sayHello() {
		return "hello Book service";
	}

	/* @return returns list of books */
	@RequestMapping(value = "/books", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Iterable<Book>> getBooks() {

		try {
			log.info("BookServices - getBooks");

			return new ResponseEntity<Iterable<Book>>(bookService.getBooks(), HttpStatus.OK);

		} catch (Exception e) {
			log.error("BookServices - getBooks", e.getMessage());
			return new ResponseEntity<Iterable<Book>>(bookService.getBooks(), HttpStatus.BAD_REQUEST);

		}

	}

	/* @return returns book based on Book Id */
	@RequestMapping(value = "/bookbyid/{id}", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Book> getBookById(@PathVariable String id) {

		try {
			log.info("BookServicesController - getBookById");

			return new ResponseEntity<Book>(bookService.getBookById(id), HttpStatus.OK);

		} catch (Exception e) {
			log.error("BookServicesController - getBookById", e.getMessage());

			return new ResponseEntity<Book>(bookService.getBookById(id), HttpStatus.BAD_REQUEST);

		}

	}

	// Create a new Note
	@PostMapping("/books")
	public ResponseEntity<Book> createNote(@Valid @RequestBody Book book) {

		try {
			log.info("BookServicesController - getBookById");

			return new ResponseEntity<Book>(bookService.save(book), HttpStatus.OK);

		} catch (Exception e) {
			log.error("BookServicesController - getBookById", e.getMessage());

			return new ResponseEntity<Book>(bookService.save(book), HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * If a book found with the given ID, it is updated and the server returns HTTP
	 * status OK. If no book found, the HTTP status Not Found (404) is returned.
	 * 
	 * @param book
	 * @param bookid
	 * @return
	 */

	@PutMapping("/books/{id}")
	public ResponseEntity<?> update(@RequestBody Book book, @PathVariable String bookid) {
		try {
			Book existProduct = bookService.getBookById(bookid);
			bookService.save(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books/{id}")
	public void delete(@PathVariable String id) {
		bookService.delete(id);
	}

	/*
	 * If some one subscribe a book, call the updateAvailability end point , to
	 * decrease -1 in available copy in book table If some one return a book, call
	 * the updateAvailability end point , to increase 1 in available copy in book
	 * table
	 * 
	 * @return returns book based on Book Id
	 */
	@RequestMapping(value = "/books/updateAvailability/{bookid}/{incremental_count}", 
			method = RequestMethod.POST, consumes = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, produces = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")

	public ResponseEntity<Book> updateAvailability(@PathVariable String bookid, @PathVariable int incremental_count) {

		try {
			log.info("BookServices - updateAvailability");

			log.info("BookServiceImpl - updateAvailability() - Bookid Subscribed=" + bookid
					+ " -incremental_count value=" + incremental_count);

			Book existProduct = bookService.getBookById(bookid);
			if (incremental_count ==-1) { // subscribe means less one
				int subscribeBookCount = existProduct.getAvailable_copies() - 1;
				if (existProduct != null
						&& (subscribeBookCount >= 0 && subscribeBookCount <= existProduct.getTotal_copies())) {
					existProduct.setAvailable_copies(subscribeBookCount);

					bookService.save(existProduct);
					log.info(bookid + " subscription have updated Succesfully");
					return new ResponseEntity<Book>(existProduct, HttpStatus.OK);
				} else {
					log.info(bookid + " subscription is not available ,as the available copies is Zero. Message dropped into Kafka Topic");
					producer.subscriberNotifyTopicById(existProduct.getBook_id());
					return new ResponseEntity<Book>(existProduct, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			} else if (incremental_count == 1) { // 1 number means return book- Plus one
				int returnBookCount = existProduct.getAvailable_copies() + 1;
				if (returnBookCount <= existProduct.getTotal_copies()) {
					existProduct.setAvailable_copies(returnBookCount);

					log.info("BookServicesController.java - updateAvailability() - copied available after book return="
							+ existProduct.getAvailable_copies());
					//Check if any of the Bookid is available in Kafka topic and notify user
					bookService.save(existProduct);
					log.info(bookid + " return have updated Succesfully.Check in Topic if any one asked to Notify");
					String isNotifyConfigured=consumer.consumesubscriberNotifyFromTopic(existProduct.getBook_id());
					if (isNotifyConfigured.equalsIgnoreCase(existProduct.getBook_id())) {
						log.info(bookid + " return have updated Succesfully. Someone subscribed to notify during book return. Notification is sent!!!");

					}
				} else {
					log.info(bookid + " return have not updated ,as the available copies and total copies are same");
					return new ResponseEntity<Book>(existProduct, HttpStatus.UNPROCESSABLE_ENTITY);
				}

				// return bookid + " return have updated Succesfully";
				return new ResponseEntity<Book>(existProduct, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("BookServicesController - updateAvailability", e.getMessage());
			log.info(bookid + " Update Availability Failed!!");

			return new ResponseEntity<Book>(bookService.getBookById(bookid), HttpStatus.BAD_REQUEST);

		}
		return null;

	}

}
