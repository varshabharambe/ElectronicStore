package com.electronics.service.impl;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
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

import com.electronics.dto.PageableResponse;
import com.electronics.dto.ProductDto;
import com.electronics.exception.ResourceNotFoundException;
import com.electronics.helper.Helper;
import com.electronics.model.Product;
import com.electronics.repository.ProductRepository;
import com.electronics.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository  productRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Value("${product.image.path}")
	private String productImagePath;
	
	@Override
	public ProductDto create(ProductDto productDto) {
		String id = UUID.randomUUID().toString();
		productDto.setProductId(id);
		productDto.setAddedDate(new Date());
		Product prod = mapper.map(productDto, Product.class);
		Product savedProd = productRepository.save(prod);
		return mapper.map(savedProd, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		Product prod = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with given id not found"));
		prod.setTitle(productDto.getTitle());
		prod.setDescription(productDto.getDescription());
		prod.setPrice(productDto.getPrice());
		prod.setDiscountedPrice(productDto.getDiscountedPrice());
		prod.setQuantity(productDto.getQuantity());
		prod.setLive(productDto.isLive());
		prod.setStock(productDto.isStock());
		prod.setProductImageName(productDto.getProductImageName());
		Product updatedProduct = productRepository.save(prod);;
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		Product prod = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with given id not found"));
		String fullProductImagePath=productImagePath + prod.getProductImageName();
		Path path = Paths.get(fullProductImagePath);
		try {
			Files.delete(path);
		} catch (NoSuchFileException ex) {
			logger.info("No such file found !!");
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		productRepository.delete(prod);
	}

	@Override
	public ProductDto getSingleProductById(String productId) {
		Product prod = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with given id not found"));
		return mapper.map(prod, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllProducts(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> prodList = productRepository.findAll(pageable);
		return Helper.getPageableResponse(prodList, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> prodList = productRepository.findByLiveTrue(pageable);
		return Helper.getPageableResponse(prodList, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> prodList = productRepository.findByTitleContaining(subTitle, pageable);
		return Helper.getPageableResponse(prodList, ProductDto.class);
	}

}
