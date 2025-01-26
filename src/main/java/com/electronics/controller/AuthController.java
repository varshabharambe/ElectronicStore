package com.electronics.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.JwtRequest;
import com.electronics.dto.JwtResponse;
import com.electronics.dto.UserDto;
import com.electronics.exception.BadApiRequestException;
import com.electronics.security.JwtHelper;

import lombok.Builder;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
		this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		String token = jwtHelper.generateToken(userDetails);
		
		UserDto user = mapper.map(userDetails, UserDto.class);
		JwtResponse response = JwtResponse.builder()
		           .jwtToken(token)
		           .user(user)
		           .build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
		}catch (BadCredentialsException e) {
			throw new BadApiRequestException("Invalid Username or password !!");
		}
	}

	@GetMapping("/current")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal){
		String name = principal.getName();
		UserDto dto = mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class);
		return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
	}
}
