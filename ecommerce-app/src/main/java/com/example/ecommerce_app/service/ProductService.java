package com.example.ecommerce_app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ecommerce_app.dto.ProductDto;

import jakarta.validation.Valid;


public interface ProductService {



	Page<ProductDto> SearchProduct(@Valid String productName, int size, int page);

}
