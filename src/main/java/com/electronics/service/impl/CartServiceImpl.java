package com.electronics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronics.dto.AddItemToCartDto;
import com.electronics.dto.CartDto;
import com.electronics.exception.BadApiRequestException;
import com.electronics.exception.ResourceNotFoundException;
import com.electronics.model.Cart;
import com.electronics.model.CartItem;
import com.electronics.model.Product;
import com.electronics.model.User;
import com.electronics.repository.CartItemRepository;
import com.electronics.repository.CartRepository;
import com.electronics.repository.ProductRepository;
import com.electronics.repository.UserRepository;
import com.electronics.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	CartRepository cartRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartDto addItemToCartDto) {
		int quantity = addItemToCartDto.getQuantity();
		String productId = addItemToCartDto.getProductId();
		
		if(quantity<=0) {
			throw new BadApiRequestException("Requested quantity is not valid !!");
		}
		
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with given id not found !!"));
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found !!"));
		Cart cart = null;
		try {
			cart = cartRepository.findByUser(user).get();
			
		}catch (NoSuchElementException e) {
			cart = new Cart();
			String cartId = UUID.randomUUID().toString();
			cart.setCartId(cartId);			
			cart.setCreatedAt(new Date());
		}
		
		//If Product is already present in cart
		AtomicReference<Boolean> updated = new AtomicReference<Boolean>(false);
		List<CartItem> cartItems = cart.getCartItems();
		cartItems = cartItems.stream()
				.map((item) -> {
					if(item.getProduct().getProductId().equals(productId)) {
						item.setQuantity(quantity);
						item.setAmount(quantity * product.getDiscountedPrice());
						updated.set(true);
					}
					return item;
				}).collect(Collectors.toList());
//		List<CartItem> updatedListCartItems = null;
//		if(cartItems!=null) {
//			updatedListCartItems = cartItems.stream()
//					.map((item) -> {
//						if(item.getProduct().getProductId().equals(productId)) {
//							item.setQuantity(quantity);
//							item.setAmount(quantity * product.getDiscountedPrice());
//							updated.set(true);
//						}
//						return item;
//					}).collect(Collectors.toList());
//		}
		
		//if product not present in cart
		if(!updated.get()) {
			CartItem cartItem = CartItem.builder()
					.quantity(quantity)
					.amount(quantity * product.getDiscountedPrice())
					.cart(cart)
					.product(product)
					.build();
			cart.getCartItems().add(cartItem);
		}
//		else {
//			cart.setCartItems(updatedListCartItems);
//		}
		
		cart.setUser(user);
		Cart savedCart = cartRepository.save(cart);
		return mapper.map(savedCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found with given id"));
		cartItemRepository.delete(cartItem);
		
	}

	@Override
	public void clearCart(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart is not present for given user!!"));
		cart.getCartItems().clear();
		cartRepository.save(cart);
	}

	@Override
	public CartDto getCartByUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart is not present for given user!!"));
		return mapper.map(cart, CartDto.class);
	}

}
