package com.lib.sub.controller;

import java.util.Arrays;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lib.sub.model.LibSubConfig;
import com.lib.sub.model.LibrarySubAppConfiguration;
import com.lib.sub.model.Subscription;
import com.lib.sub.model.SubscriptionService;
import com.lib.sub.model.SubscriptionServiceImpl;

import ch.qos.logback.classic.Logger;

@RestController
@RefreshScope
@RequestMapping("bookSubService")
public class SubscriptionServiceController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(SubscriptionServiceImpl.class.getName());

	// @Autowired
	/*
	 * @Lazy() RestTemplate restTemplate1;
	 */
	 @Autowired
		
	// @Lazy()
	  RestTemplate restTemplate;
	 
	 
	@Autowired
	LibrarySubAppConfiguration lib_subscription;

	// @Value("${book.service.path}")
	String servicePath;
	
	@Value("${booksubservice.ribbon.listOfServers}")
	String lb_servicePath;
	
	@Autowired
	private SubscriptionService subService;

	@RequestMapping("/Subscriber/hello1")
	public String sayHello() {
		return "hello Book serrvice";
	}

	@RequestMapping("/welcome")
	public String sayWelcome() {
		return "hello welcome service";
	}

	/*
	 * @return returns list of the users who have subscribed the books or return the
	 * books
	 */
	@RequestMapping(value = "getAllSubscriptions", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Iterable<Subscription>> getSubscriptions() {

		try {
			log.info("SubscriptionServiceController.java - getSubscriptions");

			return new ResponseEntity<Iterable<Subscription>>(subService.getSubscriptions(), HttpStatus.OK);

		} catch (Exception e) {
			log.error("SubscriptionService - getSubscriptions", e.getMessage());
			return new ResponseEntity<Iterable<Subscription>>(subService.getSubscriptions(), HttpStatus.BAD_REQUEST);

		}

	}

	/* @return returns subscription based on name */
	@RequestMapping(value = "subscriptionbyname/{name}", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Subscription> getSubscriptionByName(@PathVariable String name) throws Exception {

		try {
			log.info("SubscriptionServiceController.java - getSubscriptionByName");

			return new ResponseEntity<Subscription>(subService.getSubscriptionByName(name), HttpStatus.OK);

		} catch (Exception e) {
			log.error("SubscriptionServiceController - getSubscriptionByName", e.getMessage());

			return new ResponseEntity<Subscription>(subService.getSubscriptionByName(name), HttpStatus.BAD_REQUEST);

		}

	}

	/* @return returns book based on Book Id */
	@RequestMapping(value = "/bookSubByid/{id}", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Subscription> getBookSubById(@PathVariable String id) {

		try {
			log.info("BookServicesController - getBookById");

			return new ResponseEntity<Subscription>(subService.getBookSubById(id), HttpStatus.OK);

		} catch (Exception e) {
			log.error("BookServicesController - getBookById", e.getMessage());

			return new ResponseEntity<Subscription>(subService.getBookSubById(id), HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(value = "subscriptions", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Subscription> Subscription(@RequestBody com.lib.sub.model.Subscription subObj) {
		try {
			log.info("SubscriptionServiceController - Subscription");
			// TODO if notify is set to "yes" and no book is available , put a message into
			// topic.
			log.info("SubscriptionServiceController.java - updateAvailability - call Book service");
			StringBuffer message = new StringBuffer();
			message.append("Call Book Service to update the book availability count: "
					+ httpClient(subObj.getBook_id(), -1) + ", \n");

			return new ResponseEntity<Subscription>(subService.Subscription(subObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("SubscriptionServiceController - Subscription", e.getMessage());

			return new ResponseEntity<Subscription>(subService.Subscription(subObj), HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@RequestMapping(value = "returns", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Subscription> returns(@RequestBody com.lib.sub.model.Subscription subObj) {
		try {
			log.info("SubscriptionServiceController - returns");
			StringBuffer message = new StringBuffer();
			message.append("Call Book Service to update the book availability count: "
					+ httpClient(subObj.getBook_id(), 1) + ", \n");
			// TODO when user returns the book,1. update the available count +1 in book
			// table also if notify is set to "yes" and no book is available , put a message
			// into
			// topic.
			return new ResponseEntity<Subscription>(subService.Subscription(subObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("SubscriptionServiceController - Subscription", e.getMessage());

			return new ResponseEntity<Subscription>(subService.Subscription(subObj), HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	/*
	 * If some one subscribe a book, call the updateAvailability end point in
	 * BookService , to decrease -1 in available copy in book table If some one
	 * return a book, call the updateAvailability end point in BookService , to
	 * increase 1 in available copy in book table
	 * 
	 * @return returns book based on Book Id
	 */
	@RequestMapping(value = "/updateBookAvailability/{bookid}/{incremental_count}", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<String> updateAvailability(@PathVariable String bookid, @PathVariable int incremental_count) {

		try {
			log.info("SubscriptionServiceController.java - updateAvailability");

			StringBuffer message = new StringBuffer();
			log.info("SubscriptionServiceController.java - updateAvailability - call Book service");
			message.append("Call Book Service to update the book availability count: "
					+ httpClient(bookid, incremental_count) + ", \n");

			// TODO get the book available count, if zero , write the bookid into Topic
			return new ResponseEntity<String>("Book availability count updated:", HttpStatus.OK);

		} catch (Exception e) {
			log.error("BookServices - updateAvailability", e.getMessage());

			return new ResponseEntity<String>(("Update Availability Failed!!"), HttpStatus.BAD_REQUEST);

		}

	}

	private String httpClient(String bookid, int incremental_count) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		// uri = "http://librarybs/bookservice/books/updateAvailability/B1213/1";

		String uri = lib_subscription.getBook_service_euraka_path() + "/books/updateAvailability/" + bookid + "/"
				+ incremental_count;

		System.out.println("URI:" + uri);

		// String upc = restTemplate_es().exchange(uri, HttpMethod.POST, entity,
		// String.class).getBody();
		String upc = restTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
		}, "").getBody();
		return upc;
	}

	  /**
	   * Load balance between two service in differnt port numbers
	   */
	  
	  @Autowired
	  private LoadBalancerClient loadBalancer;
	  
	private String httpClient_lb(String bookid, int incremental_count) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		// uri = "http://librarybs/bookservice/books/updateAvailability/B1213/1";

		ServiceInstance instance = loadBalancer.choose("booksubservice");
		String uri = "http://" + instance.getHost() + ":" + instance.getPort()+ "/bookservice/books/updateAvailability/" + bookid + "/"
				+ incremental_count;

	/*	String uri = lib_subscription.getBook_service_euraka_path() + "/books/updateAvailability/" + bookid + "/"
				+ incremental_count;*/

		System.out.println("URI:" + uri);

		// String upc = restTemplate_es().exchange(uri, HttpMethod.POST, entity,
		// String.class).getBody();
		String upc = restTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
		}, "").getBody();
		return upc;
	}

/*	@Bean
	@LoadBalanced
	public RestTemplate restTemplate_es() {

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectionRequestTimeout(30000);
		httpRequestFactory.setConnectTimeout(30000);
		httpRequestFactory.setReadTimeout(30000);

		return new RestTemplate(httpRequestFactory);

	}*/

	/*
	 * private String callBookServiceServiceDiscovery(String id) { HttpHeaders
	 * headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * 
	 * HttpEntity<String> entity = new HttpEntity<String>(headers);
	 * 
	 * String uri = lib_subscription.getBook_service_euraka_path() + "/hello"; uri =
	 * "http://librarybs/bookservice/hello"; System.out.println("URI:" + uri);
	 * String upc = restTemplate_es().exchange(uri, HttpMethod.POST, entity, new
	 * ParameterizedTypeReference<String>() { }, id).getBody();
	 * 
	 * // String upc = restTemplate.exchange(uri, HttpMethod.GET, null, new //
	 * ParameterizedTypeReference<String>() {},id).getBody(); return upc; }
	 */
	/* Read cloud server config details */

	@GetMapping("/lib-subscription")
	public LibSubConfig lib_subscription() {
		LibSubConfig cfg = new LibSubConfig(lib_subscription.getMessage(), lib_subscription.getName());
		log.info("Spring cloud native server config .getBook_service_url-prefix value="
				+ lib_subscription.getBook_service_path());
		log.info("Spring cloud native server config .getBook_service_eurake_prefix value="
				+ lib_subscription.getBook_service_euraka_path());

		return cfg;
	}

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
