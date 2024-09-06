package com.electronics.dto;

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
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String gender;
	
	private String about;
	
	private String imageName;
}
