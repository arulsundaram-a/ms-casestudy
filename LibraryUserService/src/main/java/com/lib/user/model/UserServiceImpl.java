package com.lib.user.model;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.user.jpa.UserRepository;

import ch.qos.logback.classic.Logger;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger log = (Logger) LoggerFactory.getLogger(UserServiceImpl.class.getName());


	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<User> getUsers() {
		log.info("UserServiceImpl - getBooks() - Total Books available=" + userRepository.findAll().size());
		return userRepository.findAll();

	}

	public User getUserById(String userId) {

		log.error("UserServiceImpl - getBookById() - Id=" + userId);
		try {
			return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public User save(User user) {
		log.info("User Service Impl - save" + user.getId());

		return userRepository.save(user);
	}
/*
	@Override
	public User delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*public void delete(String id) {
		log.info("Usr Service Impl - delete" + id);

		userRepository.deleteById(id);
	}*/

}
