package com.example.ecommerce_app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_app.entity.User;

@Repository
public interface UserRepo  extends JpaRepository<User, Long>{

	Optional<User> findByEmailOrPhoneNumber(String email, Long phoneNumber);

	Optional<User> findByUserNameAndPassword(String userName, String password);

	Optional<User> findByUserId(Long userId);

}
