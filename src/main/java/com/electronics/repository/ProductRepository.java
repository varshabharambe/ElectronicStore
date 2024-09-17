package com.electronics.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	//
	Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);
}
