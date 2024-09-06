package com.electronics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.UserDto;
import com.electronics.service.UserService;

import lombok.Builder;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		UserDto userDtoResponse = userService.createUser(userDto);
		return new ResponseEntity<>(userDtoResponse,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId){
		UserDto userDtoResponse = userService.updateUser(userDto,userId);
		return new ResponseEntity<>(userDtoResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/userId")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
	    userService.deleteUser(userId);
	    ApiResponseMessage message = ApiResponseMessage
	    		.builder()
	    		.message("User deleted Successfully..!!")
	    		.success(true)
	    		.status(HttpStatus.OK)
	    		.build();
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
		return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
	}
}

