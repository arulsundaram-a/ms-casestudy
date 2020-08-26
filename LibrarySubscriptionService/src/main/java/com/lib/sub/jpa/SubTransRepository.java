package com.lib.sub.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class SubTransRepository {

	@PersistenceContext
	public EntityManager entityManager;

	
	

}
