package com.electronics.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.UserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/current")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal){
		String name = principal.getName();
		UserDto dto = mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class);
		return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
	}
}
