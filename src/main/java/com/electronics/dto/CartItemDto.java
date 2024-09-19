package com.electronics.dto;

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
public class CartItemDto {

	private int cartItemId;
	
	private ProductDto product;
	private int quantity;
	private int amount;
}
