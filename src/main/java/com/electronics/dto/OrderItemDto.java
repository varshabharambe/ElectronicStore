package com.electronics.dto;
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
public class OrderItemDto {
	
    private int orderItemId;
	private int quantity;
	private int totalPrice;
	private ProductDto product;
//	private Order order;
}
