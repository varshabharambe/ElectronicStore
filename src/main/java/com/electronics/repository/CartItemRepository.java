package com.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
