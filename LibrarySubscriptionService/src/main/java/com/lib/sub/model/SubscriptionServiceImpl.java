package com.lib.sub.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.aspectj.weaver.patterns.PerSingleton;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.lib.sub.jpa.BookSubscriptionRepository;
import com.lib.sub.jpa.SubTransRepository;

import ch.qos.logback.classic.Logger;

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {
	private static final Logger log = (Logger) LoggerFactory.getLogger(SubscriptionServiceImpl.class.getName());
	public static Map<String, Subscription> subHM = new HashMap<String, Subscription>();

	@Autowired
	SubTransRepository bookSubTransRepo;

	@Autowired
	BookSubscriptionRepository bookSubRepo;
	
	/* Return all the subscriptions objects */
	@Override
	public Iterable<Subscription> getSubscriptions() {

		log.info("BookSubscriptionServiceImpl - getSubscriptions() - Total books subscribed ="
				+ bookSubRepo.findAll().size());
		return bookSubRepo.findAll();

	}

	/* Return subscriptions objects based in the name */

	@Override
	public Subscription getSubscriptionByName(String name) throws Exception {

		log.error("SubscriptionServiceImpl - getSubscriptionByName() - name=" + name);
		try {
			return bookSubRepo.findById(name).orElseThrow(() -> new BookSubNotFoundException(name));
		} catch (BookSubNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public Subscription returns(Subscription subObj) {

		log.info("SubscriptionServiceImpl - returns - save" + subObj.getBook_id());

		return bookSubRepo.save(subObj);
		// TODO Auto-generated method stub
	}

	 
	   
	@Transactional
	public void insertWithQuery(Subscription subObj) {
/*	    entityManager.createNativeQuery("INSERT INTO subscription (book_id, subscription_name, date_subscribed) VALUES (?,?,?)")
	      .setParameter(1, subObj.getBook_id())
	      .setParameter(2, subObj.getSubscription_name())
	      .setParameter(3, subObj.getDate_subscribed())
	      .executeUpdate();*/
	    
	    this.bookSubTransRepo.entityManager.persist(subObj);
	}
	
	@Override
	public com.lib.sub.model.Subscription Subscription(Subscription subObj) {
		// TODO Auto-generated method stub
		Subscription subDbObj = bookSubRepo.save(subObj);
		//insertWithQuery(subObj);
		String successMsg = "Subscription is successfully added/updated into table for this subscriber Name="
				+ subObj.getBook_id();
		log.info("SubscriptionServiceImpl - save - new" + successMsg);

		// TODO call the book servvice /book/updateAvailability to check the if one or
		// more copies available, if we have more copied less the count -1.
		// If available copied is ZERO and notify is "yes" then put message into Mesg
		// Que or topic so that user will be notified if any body return the book

		return subObj;
	}

	@Override
	public com.lib.sub.model.Subscription save(com.lib.sub.model.Subscription sub) {
		// TODO Auto-generated method stub
		log.info("SubscriptionServiceImpl - save - new" + sub.getBook_id());

		return bookSubRepo.save(sub);

	}

	@Override
	public String delete(com.lib.sub.model.Subscription sub) {
		// TODO Auto-generated method stub
		bookSubRepo.delete(sub);
		return sub.getBook_id() + "-" + sub.getSubscription_name() + " Deleted.";

	}

	@Override
	public com.lib.sub.model.Subscription getBookSubById(String bookId) {
		// TODO Auto-generated method stub
		log.error("SubscriptionServiceImpl.java - getBookById() - Id=" + bookId);
		try {
			return bookSubRepo.findById(bookId).orElseThrow(() -> new BookSubNotFoundException(bookId));
		} catch (BookSubNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
