package com.example.ecommerce_app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_app.dto.ApiResponseDto;
import com.example.ecommerce_app.dto.ProductDto;
import com.example.ecommerce_app.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@GetMapping("/SearchProduct")
	public ResponseEntity<ApiResponseDto> SearchProduct(@Valid @RequestParam String productName,@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "0") int page) {
		logger.info("Entering");
		
		var  productDto= productService.SearchProduct(productName ,size, page);
		logger.info("Leveing");
		return ResponseEntity.ok(new ApiResponseDto("Sucess", "Product Details", 200, productDto));
		
	}

}
