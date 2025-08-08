package com.example.ecommerce_app.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_app.dto.ApiResponseDto;
import com.example.ecommerce_app.dto.CartItemDto;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/addcart")
    public ResponseEntity<ApiResponseDto> addToCart(@Valid @RequestBody CartItemDto item)throws CustomException {  	
    	logger.info("Entering");
    	var cartItemDto= cartService.addToCart(item);
    	logger.info("Leveing");
    	return ResponseEntity.ok(new ApiResponseDto("Sucess", "Product was  Add to Cart Sucessfully", 200, cartItemDto));
    }

    @GetMapping("/viewcart")
    public ResponseEntity<ApiResponseDto> viewCart(@Valid @RequestParam Long userId, @RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "0") int page) {
    	logger.info("Entering");  
    	var cartItemDto= cartService.viewCart(userId,size,page);
    	logger.info("Leveing");
    	return ResponseEntity.ok(new ApiResponseDto("Sucess", "Cart details of User", 200, cartItemDto));
    }


}
