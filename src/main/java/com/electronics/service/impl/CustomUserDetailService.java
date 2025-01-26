package com.electronics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.electronics.model.User;
import com.electronics.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//varsha@gmail.com
		//pass => abc
		System.out.println("loadUserByUsername");
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User with given email id not found !!"));
		return user;
	}

}
