package com.lib.user.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.user.model.User;
import com.lib.user.model.UserService;
import com.lib.user.model.UserServiceImpl;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("userservice")
public class  UserServices{

	private static final Logger log = (Logger) LoggerFactory.getLogger(UserServiceImpl.class.getName());

	
	@Autowired
	private UserService userService;

	@RequestMapping("/hello")
	public String sayHello() {
		return "hello User service";
	}

	/* @return returns list of users */
	@RequestMapping(value = "users", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Iterable<User>> getUsers() {

		try {
			log.info("UserServices - getUsers");

			return new ResponseEntity<Iterable<User>>(userService.getUsers(), HttpStatus.OK);

		} catch (Exception e) {
			log.error("UserServices - getUsers",e.getMessage());			
			return new ResponseEntity<Iterable<User>>(userService.getUsers(), HttpStatus.BAD_REQUEST);

		}

	}

	/* @return returns user based on user name*/
	@RequestMapping(value = "userbyid/{id}", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<User> getUserById(@PathVariable String id) {

		try {
			log.info("UserServices - getUsers");

			return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);

		} catch (Exception e) {
			log.error("UserServices - getUsers",e.getMessage());			

			return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.BAD_REQUEST);


		}

	}
	/* @return returns user based on user name*/
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<User> save(User user) {
		log.info("User Service Impl - save" + user.getId());

		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
		//return userRepository.save(user);
	}

/*	public void delete(String id) {
		log.info("Usr Service Impl - delete" + id);
		return new ResponseEntity<User>(userService.delete(id), HttpStatus.OK);

		//userRepository.deleteById(id);
	}*/

	
	/* 
	 * If some one subscribe a book, call the updateAvailability end point , to decrease -1 in available copy in book  table
	 * @return returns book based on Book Id 
	@RequestMapping(value = "user/updateNotify/{name}/{notify}",
			method = RequestMethod.POST, 
			produces = {MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json")
	public ResponseEntity<String> updateNotify(@PathVariable String name,@PathVariable String notify) {

		try {
			log.info("UserServices - updateNotify");

			return new ResponseEntity<String>(userService.updateNotify(name, notify), HttpStatus.OK);

		} catch (Exception e) {
			log.error("UserServices - updateNotify",e.getMessage());			

			return new ResponseEntity<String>(userService.updateNotify(name, notify), HttpStatus.BAD_REQUEST);

		}

	}
	*/
	/*
	 * @RequestMapping( value="bookss", method=RequestMethod.GET, produces=
	 * {MimeTypeUtils.APPLICATION_JSON_VALUE }, headers="Accept=application/json" )
	 * public @ResponseBody Iterable<Book> getBookss() {
	 * 
	 * try {
	 * 
	 * return bookService.getBooks();
	 * 
	 * }catch (Exception e) {
	 * 
	 * return null;
	 * 
	 * }
	 * 
	 * }
	 */

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "fallback method _ Invalid request";
	}

}
