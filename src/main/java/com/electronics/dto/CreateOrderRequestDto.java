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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestDto {

	private String cartId;
	private String userId;
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date deliveredDate;
}
