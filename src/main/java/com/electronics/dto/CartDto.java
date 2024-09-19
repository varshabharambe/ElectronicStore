package com.electronics.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

	private String cartId;
	
	private Date createdAt;
	
	private UserDto user;
	
	private List<CartItemDto> cartItems;
}
