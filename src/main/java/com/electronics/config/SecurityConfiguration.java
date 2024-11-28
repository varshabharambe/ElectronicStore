package com.electronics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;


@Configuration
public class SecurityConfiguration {
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user1 = User.builder()
				.username("Varsha")
				.password(passwordEncoder().encode("varsha"))
				.roles("Normal")
				.build();
		
		UserDetails user2 = User.builder()
				.username("Varsha Admin")
				.password(passwordEncoder().encode("varsha admin"))
				.roles("Admin")
				.build();
		
		
		//we will use implementation class (InMemoryUserDetailsManager) of UserDetailService coz it is a interface
		return new InMemoryUserDetailsManager(user1, user2);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
