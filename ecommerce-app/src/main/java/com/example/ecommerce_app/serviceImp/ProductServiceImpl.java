package com.example.ecommerce_app.serviceImp;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.dto.ProductDto;
import com.example.ecommerce_app.repo.ProductRepo;
import com.example.ecommerce_app.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	@Override
	public Page<ProductDto> SearchProduct(String productName,int size,int page) {
		
		var pageable =PageRequest.of(page, size);
		var products=productRepo.findByProductNameContainingIgnoreCase(productName, pageable);
		return products.map(e->{
			var dto=new ProductDto();
			BeanUtils.copyProperties(e, dto);
			return dto;
		});
	}

	
}
