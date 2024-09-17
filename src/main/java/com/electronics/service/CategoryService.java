package com.electronics.service;

import com.electronics.dto.CategoryDto;
import com.electronics.dto.PageableResponse;

public interface CategoryService {

	//create
	CategoryDto create(CategoryDto categoryDto);
	
	//update
	CategoryDto update(CategoryDto categoryDto, String catId);
	
    //delete 
	void delete(String catId);
	
    //get all
	PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize, String sortBy, String sortDir);
	
    //get single cat detail
	CategoryDto getSingleCategoryById(String catId);
	
    //search
}
