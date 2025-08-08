package com.example.ecommerce_app.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_app.entity.Cart;
@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{

	List<Cart> findByUserId(Long userId, Pageable pageable);

	void deleteByProductId(Long productId);

	List<Cart> findByUserId(Long userId);

	Optional<Cart> findByCartId(Long cartId);

	Optional<Cart> findByCartIdAndUserId(Long cartId, Long userId);

}
