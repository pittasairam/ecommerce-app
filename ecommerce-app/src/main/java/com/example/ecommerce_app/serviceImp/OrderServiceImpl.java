package com.example.ecommerce_app.serviceImp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.controller.CartController;
import com.example.ecommerce_app.dto.OrderDto;
import com.example.ecommerce_app.dto.OrderItemDto;
import com.example.ecommerce_app.dto.OrderRequestDto;
import com.example.ecommerce_app.entity.Cart;
import com.example.ecommerce_app.entity.Order;
import com.example.ecommerce_app.entity.OrderItem;
import com.example.ecommerce_app.feignclint.BankFeignClient;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.repo.CartRepo;
import com.example.ecommerce_app.repo.OrderRepository;
import com.example.ecommerce_app.service.OrderService;

import jakarta.transaction.Transactional;
@Service
public class OrderServiceImpl implements OrderService{
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


	@Autowired
    private OrderRepository orderRepository;
	@Autowired
	private BankFeignClient bankFeignClient;
	@Autowired
	private CartRepo cartRepo;
	
	@Override
	@Transactional
	public OrderDto placeOrder(OrderRequestDto orderRequestDto) throws CustomException {
	    logger.info("Entering placeOrder");

	    var cartList = cartRepo.findByUserId(orderRequestDto.getUserId());
	    var cartOptional = cartRepo.findByCartId(orderRequestDto.getCartId());

	    if (cartList.isEmpty() || cartOptional.isEmpty()) {
	        throw new CustomException("Item is not available in cart");
	    }

	    var order = new Order();
	    order.setUserId(orderRequestDto.getUserId());
	    order.setOrderDate(LocalDateTime.now());
	    order.setCartId(orderRequestDto.getCartId());


	    var itemDtos = Optional.ofNullable(orderRequestDto.getItems()).orElse(List.of());

	    var items = itemDtos.isEmpty()
	        ? cartOptional.stream()
	            .map(cart -> {
	                var item = new OrderItem();
	                item.setOrderId(order.getId());
	                item.setProductId(cart.getProductId());
	                item.setProductName(cart.getProductName());
	                item.setQuantity(cart.getQuantity().intValue());
	                item.setPrice(cart.getPrice().doubleValue());
	                return item;
	            })
	            .toList()
	        : itemDtos.stream()
	            .map(itemDto -> {
	                var item = new OrderItem();
	                item.setOrderId(order.getId());
	                item.setProductId(itemDto.getProductId());
	                item.setProductName(itemDto.getProductName());
	                item.setQuantity(itemDto.getQuantity());
	                item.setPrice(itemDto.getPrice());
	                return item;
	            })
	            .toList();

	    var totalAmount = items.stream()
	        .mapToDouble(i -> i.getPrice() * i.getQuantity())
	        .sum();

	    order.setTotalAmount(totalAmount);
	    order.setItems(items);

	    var amount = BigDecimal.valueOf(totalAmount);

	    try {
	        bankFeignClient.doTransaction(orderRequestDto.getFromAccount(), 9999999999L, amount);
	    } catch (Exception e) {
	        logger.error("Transaction failed", e);
	        throw new CustomException("Transaction failed: Insufficient Amount");
	    }

	    orderRepository.saveOrders(order.getOrderDate(), order.getTotalAmount(), order.getUserId());

	    items.stream()
	        .map(OrderItem::getProductId)
	        .forEach(cartRepo::deleteByProductId);

	    logger.info("Leaving placeOrder");
	    return convertToDto(order);
	}




	   private OrderDto convertToDto(Order order) {
		    logger.info("Entering convertToDto");

		    var dto = new OrderDto();
		    dto.setOrderId(order.getId());
		    dto.setUserId(order.getUserId());
		    dto.setOrderDate(order.getOrderDate());
		    dto.setTotalAmount(order.getTotalAmount());

		    dto.setItems(
		        Optional.ofNullable(order.getItems())
		            .orElse(Collections.emptyList())
		            .stream()
		            .map(item -> {
		                var itemDto = new OrderItemDto();
		                itemDto.setProductId(item.getProductId());
		                itemDto.setProductName(item.getProductName());
		                itemDto.setQuantity(item.getQuantity());
		                itemDto.setPrice(item.getPrice());
		                return itemDto;
		            })
		            .toList()
		    );

		    logger.info("Leaving convertToDto");
		    return dto;
		}

	
}
