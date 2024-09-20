package com.electronics.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
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

	@NotBlank(message="Cart id is required !!")
	private String cartId;
	
	@NotBlank(message="User id is required !!")
	private String userId;
	
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	
	@NotBlank(message="Address is required !!")
	private String billingAddress;
	
	@NotBlank(message="Phone number is required !!")
	private String billingPhone;
	
	@NotBlank(message="Name is required !!")
	private String billingName;
	private Date deliveredDate;
}
