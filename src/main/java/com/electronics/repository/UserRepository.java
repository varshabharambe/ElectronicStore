package com.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
