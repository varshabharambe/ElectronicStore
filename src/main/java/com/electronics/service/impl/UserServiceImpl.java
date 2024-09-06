package com.electronics.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.electronics.dto.UserDto;
import com.electronics.model.User;
import com.electronics.repository.UserRepository;
import com.electronics.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		String userId = UUID.randomUUID().toString();
		userDto.setId(userId);
		User user = dtoToEntity(userDto);
		User savedUser = userRepository.save(user);
		UserDto dto = entityToDto(savedUser);
		return dto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with given id not found"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		
		UserDto updatedDto = entityToDto(user);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with given id not found"));
		userRepository.delete(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> usersDtoList = users.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
		return usersDtoList;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with given id not found"));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with given email Id"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}
	
	private UserDto entityToDto(User user) {
//		UserDto userDto = UserDto.builder()
//				.id(user.getId())
//				.name(user.getName())
//				.email(user.getEmail())
//				.password(user.getPassword())
//				.gender(user.getGender())
//				.about(user.getAbout())
//				.imageName(user.getImageName())
//				.build();
//		return userDto;
		return mapper.map(user, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
//		User user = User.builder()
//				.id(userDto.getId())
//				.name(userDto.getName())
//				.email(userDto.getEmail())
//				.password(userDto.getPassword())
//				.gender(userDto.getGender())
//				.about(userDto.getAbout())
//				.imageName(userDto.getImageName())
//				.build();
//		return user;
		return mapper.map(userDto, User.class);
	}

}
