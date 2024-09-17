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
import com.electronics.dto.ImageResponse;
import com.electronics.dto.PageableResponse;
import com.electronics.dto.ProductDto;
import com.electronics.service.FileService;
import com.electronics.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FileService fileService;
	
	@Value("${product.image.path}")
	private String productImagePath;
	
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
		ProductDto product = productService.create(productDto);
		return new ResponseEntity<ProductDto>(product,HttpStatus.CREATED);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable("productId")String productId){
		ProductDto product = productService.update(productDto, productId);
		return new ResponseEntity<ProductDto>(product,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId")String productId){
		productService.delete(productId);
		ApiResponseMessage response = ApiResponseMessage.builder()
				.message("Product deleted successfully !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC",required = false) String sortDir
			){
		PageableResponse<ProductDto> response = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable("productId") String productId){
		ProductDto product = productService.getSingleProductById(productId);
		return new ResponseEntity<ProductDto>(product,HttpStatus.OK);
	}
	
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC",required = false) String sortDir
			){
		PageableResponse<ProductDto> response = productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/search/{subTitle}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
			@PathVariable("subTitle") String subTitle,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC",required = false) String sortDir
			){
		PageableResponse<ProductDto> response = productService.searchByTitle(subTitle,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage") MultipartFile file, @PathVariable("productId") String productId) throws IOException{
		String imageName = fileService.uploadFile(file, productImagePath);
		ProductDto prod = productService.getSingleProductById(productId);
		prod.setProductImageName(imageName);
		ProductDto updatedProduct = productService.update(prod, productId);
		
		ImageResponse response = ImageResponse.builder()
				.imageName(imageName)
				.message("Product image uploaded successfully !!")
				.status(HttpStatus.CREATED)
				.success(true)
				.build();
		return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/image/{productId}")
	public void serveProductimage(@PathVariable("productId") String productId, HttpServletResponse response) throws IOException {
		ProductDto prod = productService.getSingleProductById(productId);
		InputStream resource = fileService.getResource(productImagePath, prod.getProductImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
