package com.electronics.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//order is reserved keyword in my sql so we have to use backticks to escape it
@Table(name="`order`")
public class Order {

	@Id
	private String orderId;
	
	private String orderStatus;
	private String paymentStatus;
	private int orderAmount;
	
	@Column(length=1000)
	private String billingAddress;
	
	private String billingPhone;
	
	private String billingName;
	
	private Date orderedDate;
	
	private Date deliveredDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	
	@OneToMany(mappedBy = "order", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
}
