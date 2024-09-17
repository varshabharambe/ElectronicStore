package com.electronics.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronics.dto.CategoryDto;
import com.electronics.dto.PageableResponse;
import com.electronics.exception.ResourceNotFoundException;
import com.electronics.helper.Helper;
import com.electronics.model.Category;
import com.electronics.repository.CategoryRepository;
import com.electronics.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		String id = UUID.randomUUID().toString();
		categoryDto.setCategoryId(id);
		Category category =mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String catId) {
		Category cat = categoryRepository.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("Category with given id not found !!"));
		cat.setTitle(categoryDto.getTitle());
		cat.setDescription(categoryDto.getDescription());
		cat.setCoverImage(categoryDto.getCoverImage());
		Category savedCat = categoryRepository.save(cat);
		return mapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public void delete(String catId) {
		Category cat = categoryRepository.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("Category with given id not found !!"));
		categoryRepository.delete(cat);
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending()  :Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Category> catList = categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> response = Helper.getPageableResponse(catList, CategoryDto.class);
		return response;
	}

	@Override
	public CategoryDto getSingleCategoryById(String catId) {
		Category cat = categoryRepository.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("Category with given id not found !!"));
		return mapper.map(cat, CategoryDto.class);
	}

}
