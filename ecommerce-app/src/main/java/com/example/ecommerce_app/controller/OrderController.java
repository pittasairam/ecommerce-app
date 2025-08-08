package com.example.ecommerce_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_app.dto.ApiResponseDto;
import com.example.ecommerce_app.dto.OrderDto;
import com.example.ecommerce_app.dto.OrderRequestDto;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")

public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<ApiResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto request) throws CustomException {
    	logger.info("Entering");
    	var order = orderService.placeOrder(request);
    	logger.info("Leveing");
        return ResponseEntity.ok(new ApiResponseDto("Sucess", "order was sucess", 200, order));
    }

}
