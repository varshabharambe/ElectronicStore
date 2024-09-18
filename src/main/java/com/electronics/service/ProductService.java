package com.electronics.service;

import java.util.List;

import com.electronics.dto.PageableResponse;
import com.electronics.dto.ProductDto;

public interface ProductService {

	//create
	ProductDto create(ProductDto productDto);
	
	//update
	ProductDto update(ProductDto productDto,String productId);
	
	//delete
	void delete(String productId);
	
	//get Single
	ProductDto getSingleProductById(String productId);
	
	//get all
	PageableResponse<ProductDto> getAllProducts(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//Get all live
	PageableResponse<ProductDto> getAllLiveProducts(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//search product
	PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

	ProductDto createProductWithCategory(ProductDto productDto, String categoryId);
	
	ProductDto addProductInCategory(String productId,String categoryId);
	
	PageableResponse<ProductDto> getAllProductsInCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}
