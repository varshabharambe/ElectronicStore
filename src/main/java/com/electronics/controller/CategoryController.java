package com.electronics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.CategoryDto;
import com.electronics.dto.PageableResponse;
import com.electronics.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	//create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto catDto = categoryService.create(categoryDto);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") String categoryId){
		CategoryDto catDto = categoryService.update(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId){
		categoryService.delete(categoryId);
		ApiResponseMessage res = ApiResponseMessage.builder()
				.message("Category deleted successfully !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMessage>(res,HttpStatus.OK);
	}
	
	//get all
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
			@RequestParam(value="pageNumber", defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10",required = false)int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title",required = false)String sortBy,
			@RequestParam(value="sortDir", defaultValue = "ASC",required = false)String sortDir
			){
		
		PageableResponse<CategoryDto> response = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<CategoryDto>>(response,HttpStatus.OK);
		
	}
	
	//get single category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> createCategory(@PathVariable("categoryId") String categoryId){
		CategoryDto catDto = categoryService.getSingleCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.CREATED);
	}
}
