package com.electronics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	List<User> findByNameContaining(String keyword);
}
