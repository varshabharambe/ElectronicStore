package com.electronics.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronics.dto.UserDto;
import com.electronics.exception.ResourceNotFoundException;
import com.electronics.model.User;
import com.electronics.repository.UserRepository;
import com.electronics.service.UserService;

@Service
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
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		
		userRepository.save(user);
		
		UserDto updatedDto = entityToDto(user);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));
		userRepository.delete(user);
	}

	@Override
	public List<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending()  :Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> pageRes = userRepository.findAll(pageable);
		List<User> users = pageRes.getContent();
		List<UserDto> usersDtoList = users.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
		return usersDtoList;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email Id"));
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
