package com.electronics.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String header = request.getHeader("Authorization");
		 logger.info("header : {}", header);
		 
		 String userName = null;
		 String token = null;
		 
		 if(header != null && header.startsWith("Bearer ")) {
			 token = header.substring(7);
			 try {
				 
				 userName = jwtHelper.getUsernameFromToken(token);
				 
			 }catch (IllegalArgumentException e) {
				 logger.info("Illegal argument while fetching username from jwt token!");
				 e.printStackTrace();
			 }catch (ExpiredJwtException e) {
				 logger.info("Token is expired!");
				 e.printStackTrace();
			 }catch (MalformedJwtException e) {
				 logger.info("Malformed token! Invalid !");
				 e.printStackTrace();
			 }catch (Exception e) {
				 e.printStackTrace(); 
			}
		 }else {
			 logger.info("Invalid Token! ");
		 }
		 
		 if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			 boolean isValid = this.jwtHelper.validateToken(token, userDetails);
			 
			 if(isValid) {
				 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(authentication);
			 }else {
				 logger.info("Invalid token !!");
			 }
		 }
		 
		 filterChain.doFilter(request, response);
		
	}

}
