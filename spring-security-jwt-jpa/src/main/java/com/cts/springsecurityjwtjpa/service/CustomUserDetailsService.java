package com.cts.springsecurityjwtjpa.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.springsecurityjwtjpa.model.UserDto;
import com.cts.springsecurityjwtjpa.model.UserModel;
import com.cts.springsecurityjwtjpa.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not Found with username "+ username);
		}
		return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}
	
	public UserModel save(UserDto user) {
		UserModel newUser = new UserModel();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		return userRepository.save(newUser);
	}
	
}
