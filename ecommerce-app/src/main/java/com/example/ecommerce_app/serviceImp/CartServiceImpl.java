package com.example.ecommerce_app.serviceImp;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.controller.OrderController;
import com.example.ecommerce_app.dto.CartItemDto;
import com.example.ecommerce_app.entity.Cart;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.repo.CartRepo;
import com.example.ecommerce_app.repo.ProductRepo;
import com.example.ecommerce_app.repo.UserRepo;
import com.example.ecommerce_app.service.CartService;
@Service
public class CartServiceImpl implements CartService{
	
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public CartItemDto addToCart(CartItemDto item) throws CustomException {
	    logger.info("Entering addToCart");

	    var cart = new Cart();

	    var userExists = userRepo.findByUserId(item.getUserId())
	        .orElseThrow(() -> new CustomException("User does not exist. Please register first."));

	    var product = productRepo.findByProductId(item.getProductId())
	        .orElseThrow(() -> new CustomException("Product does not exist"));

	    if (product.getProductQty() == 0L) {
	        throw new CustomException("Product stock is not available");
	    }

	    var existingCartItem = cartRepo.findByCartIdAndUserId(item.getCartId(), item.getUserId());

	    double price = item.getQuantity() * product.getProductPrice();

	    if (existingCartItem.isPresent()) {
	        var existing = existingCartItem.get();
	        item.setQuantity(item.getQuantity() + existing.getQuantity());
	        price = item.getQuantity() * product.getProductPrice();
	    }

	    item.setPrice((long) price);

	    BeanUtils.copyProperties(item, cart);
	    cartRepo.save(cart);
	    BeanUtils.copyProperties(cart, item);

	    logger.info("Leaving addToCart");
	    return item;
	}



	@Override
	public List<CartItemDto> viewCart(Long userId,int size,int page) {
		logger.info("Entering");
		var pageable =PageRequest.of(page, size);
		var items = cartRepo.findByUserId(userId,pageable);
		logger.info("Leveing");
		return items.stream().map(e -> {
			var dto = new CartItemDto();
			BeanUtils.copyProperties(e, dto);
			return dto;
			}).toList();
	}





}
