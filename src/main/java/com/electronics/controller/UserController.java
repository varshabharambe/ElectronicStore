package com.electronics.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.ImageResponse;
import com.electronics.dto.PageableResponse;
import com.electronics.dto.UserDto;
import com.electronics.service.FileService;
import com.electronics.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDtoResponse = userService.createUser(userDto);
		return new ResponseEntity<>(userDtoResponse,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId){
		UserDto userDtoResponse = userService.updateUser(userDto,userId);
		return new ResponseEntity<>(userDtoResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
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
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
			){
		return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
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
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(
			@RequestParam("userImage") MultipartFile file, @PathVariable("userId") String userId
			) throws IOException{
		String imageName = fileService.uploadFile(file, imageUploadPath);
		
		UserDto user= userService.getUserById(userId);
		user.setImageName(imageName);
		UserDto updatedUser= userService.updateUser(user, userId);
		
		
		ImageResponse response = ImageResponse.builder()
				.imageName(imageName)
				.message("Image Uploaded Successfully !!")
				.status(HttpStatus.CREATED)
				.success(true)
				.build();
		return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable("userId") String userId, HttpServletResponse response) throws IOException {
		UserDto user = userService.getUserById(userId);
		InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}

