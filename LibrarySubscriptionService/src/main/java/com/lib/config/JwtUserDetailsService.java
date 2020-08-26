package com.lib.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	private String password;
	
	@Autowired
	private  PasswordEncoder passwordEncoder;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public UserDetails loadUserByUsername(String loginUserId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		// return new User("foo","bar",new ArrayList<>());
		// return new User("javainuse",
		// "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
		// new ArrayList<>());
		//we can connect to DB and fetch the details
		if ("e1010675".equals(loginUserId)) {
			// return new User("e1010675",
			// "$2a$10$klcTloXLnDOxdRDjFJfb4eS8V9VnORO7VH7CC0TRN67gqTu6ccwSO", new
			// ArrayList<>());
			//String password=(String)SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
			//System.out.println("password"+password);
			return new User("e1010675", passwordEncoder.encode("Auguest2020"), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Invalid loginUserId : " + loginUserId);
		}

	}

}
