package com.lib.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "users";
	private static final String BOOKSUB_NOTIFY = "book_sub_notify";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

/*	public void sendMessage_UserTopic(String message) {
		logger.info(String.format("$$ -> Producing message --> %s", message));
		this.kafkaTemplate.send(TOPIC, message);
	}
	*/
/*	public void sendMessage_CustomerTopic(String message) {
		logger.info(String.format("$$ -> Producing message --> %s", message));
		this.kafkaTemplate.send(CUSTOMERTOPIC, message);
	}*/
	
	public void subscriberNotifyTopic(String bookIDSubscribed) {
		logger.info(String.format("$$ -> Subscriber wants to Notify when this book is return --> %s", bookIDSubscribed));
		this.kafkaTemplate.send(BOOKSUB_NOTIFY, bookIDSubscribed);
	}
	public void subscriberNotifyTopicById(String bookIDSubscribed) {
		logger.info(String.format("$$ -> Subscriber wants to Notify when this book is return --> %s", bookIDSubscribed));
		this.kafkaTemplate.send(BOOKSUB_NOTIFY, bookIDSubscribed,bookIDSubscribed);
	}
}