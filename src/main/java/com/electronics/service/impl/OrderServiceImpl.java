package com.electronics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronics.dto.CreateOrderRequestDto;
import com.electronics.dto.OrderDto;
import com.electronics.dto.PageableResponse;
import com.electronics.exception.BadApiRequestException;
import com.electronics.exception.ResourceNotFoundException;
import com.electronics.model.Cart;
import com.electronics.model.CartItem;
import com.electronics.model.Order;
import com.electronics.model.OrderItem;
import com.electronics.model.User;
import com.electronics.repository.CartRepository;
import com.electronics.repository.OrderRepository;
import com.electronics.repository.UserRepository;
import com.electronics.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public OrderDto createOrder(CreateOrderRequestDto dto) {
		User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found with given id !!"));
		Cart cart = cartRepository.findById(dto.getCartId()).orElseThrow(()-> new ResourceNotFoundException("Cart with given id not found !!"));
		List<CartItem> cartItems = cart.getCartItems();
		if(cartItems.size()<=0) {
			throw new BadApiRequestException("Invalid number of items in cart !!");
		}
		Order order = Order.builder()
				.billingName(dto.getBillingName())
				.billingPhone(dto.getBillingPhone())
				.billingAddress(dto.getBillingAddress())
				.orderedDate(new Date())
				.deliveredDate(dto.getDeliveredDate())
				.paymentStatus(dto.getPaymentStatus())
				.orderStatus(dto.getOrderStatus())
				.orderId(UUID.randomUUID().toString())
				.user(user)
				.build();
		
		//convert all cart items in order items
		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
		List<OrderItem> orderItems = cartItems.stream().map((cartItem) -> {
			OrderItem orderItem = OrderItem.builder()
					.quantity(cartItem.getQuantity())
					.product(cartItem.getProduct())
					.totalPrice(cartItem.getAmount())
					.order(order)
					.build();
			orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
			return orderItem;
		}).collect(Collectors.toList());
		
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderAmount.get());
		
		//clear cart and save
		cart.getCartItems().clear();
		cartRepository.save(cart);
		
		//save order
		Order savedOrder = orderRepository.save(order);
		
		return mapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order with given id not found !!"));
		orderRepository.delete(order);
		
	}

	@Override
	public List<OrderDto> getOrdersOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

}
