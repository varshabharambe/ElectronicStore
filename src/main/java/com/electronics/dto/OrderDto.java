package com.electronics.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.electronics.model.OrderItem;
import com.electronics.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class OrderDto {
	
    private String orderId;
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	private int orderAmount;
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderedDate=new Date();
	private Date deliveredDate;
//	private UserDto user;
	private List<OrderItemDto> orderItems = new ArrayList<>();
}
