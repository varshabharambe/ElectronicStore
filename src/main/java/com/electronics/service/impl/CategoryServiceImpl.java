package com.electronics.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Value("${category.image.path}")
	private String categoryImagePath;
	
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
		String fullCatImagePath = categoryImagePath + cat.getCoverImage();
		Path path = Paths.get(fullCatImagePath);
		try {
			Files.delete(path);
		} catch (NoSuchFileException ex) {
			logger.info("No such file found !!");
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
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
