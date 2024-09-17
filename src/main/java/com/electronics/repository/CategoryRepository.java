package com.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{

}
