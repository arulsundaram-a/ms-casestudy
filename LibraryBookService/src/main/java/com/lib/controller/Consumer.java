package com.lib.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	private static final String TOPIC = "users";
	private static final String CUSTOMERTOPIC = "customers";
	private static final String BOOKSUB_NOTIFY = "book_sub_notify";

/*	@KafkaListener(topics = TOPIC, groupId = "group_id")
	public void consume(String message) {
		logger.info(String.format("$$ -> Consumed Message-group_id -> %s", message));
	}
	
	@KafkaListener(topics = TOPIC, groupId = "group_id4")
	public void consume1(String message) {
		logger.info(String.format("$$ -> Consumed Messagesss-group_id4 -> %s", message));
	}
	*/
	@KafkaListener(topics = BOOKSUB_NOTIFY, groupId = "notifygroup_id1")
	public String consumesubscriberNotifyFromTopic(String bookIDSubscribed) {
		logger.info(String.format("$$ -> BookId available in Topic -group_id3.Notify User -> %s", bookIDSubscribed));
		return bookIDSubscribed;
	}
	
	@KafkaListener(topics = BOOKSUB_NOTIFY, groupId = "notifygroup_id2")

	public String consumesubscriberNotifyFromTopicId(ConsumerRecord<String,String> bookIDSubscribedObj) {
		logger.info(String.format("$$ -> Consumed Messagesss-group_id3 -> %s", bookIDSubscribedObj.key()+"--Value"+bookIDSubscribedObj.value()));
		return  bookIDSubscribedObj.value();
	}
}