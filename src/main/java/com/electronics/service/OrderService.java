package com.electronics.service;

import java.util.List;

import com.electronics.dto.CreateOrderRequestDto;
import com.electronics.dto.OrderDto;
import com.electronics.dto.PageableResponse;

public interface OrderService {
	
	//create order
	OrderDto createOrder(CreateOrderRequestDto createOrderRequestDto);
	
	//remove order
	void removeOrder(String orderId);
	
	//get all orders of a user
	List<OrderDto> getOrdersOfUser(String userId);
	
	//get all orders of all users for admin
	PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	OrderDto update(CreateOrderRequestDto createOrderRequestDto,String orderId);
	
}
