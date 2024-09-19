package com.electronics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.Cart;
import com.electronics.model.User;

public interface CartRepository extends JpaRepository<Cart, String>{
	
	Optional<Cart> findByUser(User user);
}
