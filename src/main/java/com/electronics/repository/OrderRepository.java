package com.electronics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.Order;
import com.electronics.model.User;

public interface OrderRepository extends JpaRepository<Order, String>{
     
	List<Order> findByUser(User user);
}
