package com.electronics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;


@Configuration
public class SecurityConfiguration {
	
	//we can use directly UserDetailsService instead CustomUserDetailService because CustomUserDetailService is implementing UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User.builder()
//				.username("Varsha")
//				.password(passwordEncoder().encode("varsha"))
//				.roles("Normal")
//				.build();
//		
//		UserDetails user2 = User.builder()
//				.username("Varsha Admin")
//				.password(passwordEncoder().encode("varsha admin"))
//				.roles("Admin")
//				.build();
//		
//		
//		//we will use implementation class (InMemoryUserDetailsManager) of UserDetailService coz it is a interface
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().authenticated().and()
//		          .formLogin()
//		          .loginPage("login.html")
//		          .loginProcessingUrl("/process-url")
//		          .defaultSuccessUrl("/dashboard")
//		          .failureUrl("/error")
//		          .and()
//		          .logout()
//		          .logoutUrl("/do-logout");
//		return http.build();
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .cors().disable()
		    .authorizeRequests()
		    .anyRequest()
		    .authenticated()
		    .and()
		    .httpBasic();
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
