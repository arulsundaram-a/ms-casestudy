package com.lib.sub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.sub.model.Subscription;

@Repository
public interface BookSubscriptionRepository extends JpaRepository<Subscription, String> {

}