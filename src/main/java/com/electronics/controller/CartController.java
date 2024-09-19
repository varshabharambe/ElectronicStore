package com.electronics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.AddItemToCartDto;
import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.CartDto;
import com.electronics.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartDto dto){
		CartDto cartDto = cartService.addItemToCart(userId, dto);
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/item/{cartItemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int cartItemId){
		cartService.removeItemFromCart(userId, cartItemId);
		ApiResponseMessage res=ApiResponseMessage.builder()
				.message("Cart item removed successfully !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMessage>(res,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
		cartService.clearCart(userId);
		ApiResponseMessage res=ApiResponseMessage.builder()
				.message("Cart is empty now !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMessage>(res,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){
		CartDto cartDto = cartService.getCartByUser(userId);
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}
}
