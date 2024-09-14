package com.electronics.service;

import java.util.List;

import com.electronics.dto.UserDto;

public interface UserService {

	public UserDto createUser(UserDto userDto);
	
	public UserDto updateUser(UserDto userDto, String userId);
	
	public void deleteUser(String userId);
	
	public List<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	public UserDto getUserById(String userId);
	
	public UserDto getUserByEmail(String email);
	
	public List<UserDto> searchUser(String keyword);
	
	
}
