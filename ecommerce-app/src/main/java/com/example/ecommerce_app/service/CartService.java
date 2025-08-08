package com.example.ecommerce_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecommerce_app.dto.CartItemDto;
import com.example.ecommerce_app.gobleexp.CustomException;

public interface CartService {

	CartItemDto addToCart(CartItemDto item)throws CustomException;

	List<CartItemDto>  viewCart(Long userId,int size,int page);

}
