package com.example.ecommerce_app.repo;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_app.entity.Product;
@Repository
public interface ProductRepo  extends JpaRepository<Product, Long>{

	Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

	Optional<Product> findByProductId(Long productId);
	

}
