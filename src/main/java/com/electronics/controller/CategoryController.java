package com.electronics.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronics.dto.ApiResponseMessage;
import com.electronics.dto.CategoryDto;
import com.electronics.dto.ImageResponse;
import com.electronics.dto.PageableResponse;
import com.electronics.service.CategoryService;
import com.electronics.service.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FileService fileService;
	
	@Value("${category.image.path}")
	private String categoryImagePath;
	
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
	
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryImage(
			@RequestParam("categoryImage")MultipartFile file, 
			@PathVariable("categoryId")String categoryId) throws IOException{
		String categoryImageName = fileService.uploadFile(file, categoryImagePath);
		
		CategoryDto catDto = categoryService.getSingleCategoryById(categoryId);
		catDto.setCoverImage(categoryImageName);
		CategoryDto updatedCatDto = categoryService.update(catDto, categoryId);
		
		ImageResponse response = ImageResponse.builder()
				.imageName(categoryImageName)
				.message("Category image Uploaded Successfully !!")
				.status(HttpStatus.CREATED)
				.success(true)
				.build();
		return new ResponseEntity<ImageResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/image/{categoryId}")
	public void serveCategoryImage(@PathVariable("categoryId") String categoryId,HttpServletResponse response) throws IOException{
		CategoryDto cat = categoryService.getSingleCategoryById(categoryId);
		InputStream resource = fileService.getResource(categoryImagePath, cat.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
