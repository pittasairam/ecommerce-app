package com.example.ecommerce_app.repo;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_app.entity.Order;



@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{
	/*
	 * @Query(name="INSERT INTO ORDER(order_date,total_amount,user_id) values ())
	 * void save(LocalDateTime localDateTime,Double price,Long UserID);
	 */

@Modifying
@Query(value = "INSERT INTO test.orders (order_date, total_amount, user_id) VALUES (:orderDate, :totalAmount, :userId)", nativeQuery = true)
void saveOrders( LocalDateTime orderDate, Double totalAmount,Long userId);

	List<Order> findByUserIdAndOrderDateAfter(Long userId, LocalDateTime fromDate, Pageable pageable);

	

}
