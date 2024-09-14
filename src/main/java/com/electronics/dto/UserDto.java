package com.electronics.dto;

import com.electronics.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	
	private String id;
	
	@Size(min=3, max = 30, message= "Invalid Name !!")
	private String name;
	
//	@Email(message = "Invalid User Email !!")
	@Pattern(regexp="^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message="Invalid User Email !!")
	@NotBlank(message = "Email is required !!")
	private String email;
	
	@NotBlank(message = "Password is required !!")
	private String password;
	
	@Size(min = 4, max=6, message="Invalid gender !!")
	private String gender;
	
	@NotBlank(message="Write something about yourself !! ")
	private String about;
	
	@ImageNameValid
	private String imageName;
}
