package com.electronics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.CreateOrderRequestDto;
import com.electronics.dto.OrderDto;
import com.electronics.dto.PageableResponse;
import com.electronics.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequestDto dto){
		OrderDto order = orderService.createOrder(dto);
		return new ResponseEntity<OrderDto>(order,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
		orderService.removeOrder(orderId);
		ApiResponseMessage response = ApiResponseMessage.builder()
				.message("Order is removed !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId){
		List<OrderDto> orders = orderService.getOrdersOfUser(userId);
		return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
			@RequestParam(value="pageNumber", defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10",required = false)int pageSize,
			@RequestParam(value="sortBy", defaultValue = "orderedDate",required = false)String sortBy,
			@RequestParam(value="sortDir", defaultValue = "desc",required = false)String sortDir
			){
		PageableResponse<OrderDto> orders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<OrderDto>>(orders,HttpStatus.OK);
	}
	
	@PostMapping("/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody CreateOrderRequestDto dto,@PathVariable String orderId){
		OrderDto order = orderService.update(dto, orderId);
		return new ResponseEntity<OrderDto>(order,HttpStatus.OK);
	}
}
