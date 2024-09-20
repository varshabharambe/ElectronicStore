package com.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
