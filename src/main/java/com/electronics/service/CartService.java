package com.electronics.service;

import com.electronics.dto.AddItemToCartDto;
import com.electronics.dto.CartDto;

public interface CartService {

//	add item to cart
//	case 1: if cart does not exist then create cart and add item
//	case 2:if cart already exist then add item to cart
	
	CartDto addItemToCart(String userId, AddItemToCartDto addItemToCartDto);
	
	void removeItemFromCart(String userId, int cartItemId);
	
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);
}
