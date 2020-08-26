package com.lib.user.model;

public interface UserService {
	public Iterable<User> getUsers();

	public User getUserById(String id);
	public User save(User user);
	//public User delete(String id);

}
