package com.lib.sub.model;

import com.lib.sub.model.Subscription;

public interface SubscriptionService {

	public Iterable<Subscription> getSubscriptions();

	public Subscription getSubscriptionByName(String name) throws Exception;
	public Subscription getBookSubById(String id);

	
	public Subscription Subscription(Subscription subObj);

	public Subscription returns(Subscription subObj);

	public Subscription save(Subscription subObj);

	public String delete(Subscription subObj);

}
