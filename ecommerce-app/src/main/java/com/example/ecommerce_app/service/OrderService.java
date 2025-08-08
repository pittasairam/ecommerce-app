package com.example.ecommerce_app.service;

import com.example.ecommerce_app.dto.OrderDto;
import com.example.ecommerce_app.dto.OrderRequestDto;
import com.example.ecommerce_app.gobleexp.CustomException;

public interface OrderService {

	OrderDto placeOrder(OrderRequestDto request) throws CustomException;

}
