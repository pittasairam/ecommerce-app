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
import com.example.ecommerce_app.dto.UserDto;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.service.UserService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	private UserService userService;
	@PostMapping("/register")
	public ResponseEntity<ApiResponseDto> register(@Valid @RequestBody UserDto userDto) throws CustomException {
		logger.info("Entering");
		var ustDto= userService.register(userDto);
		logger.info("Leveing");
		return ResponseEntity.ok(new ApiResponseDto("Sucess", "user has been register Sucessfully", 200, ustDto));	
	}
	@GetMapping("/login")
	public ResponseEntity<ApiResponseDto> login(@Valid @RequestParam String userName,@RequestParam String password) throws CustomException {
		logger.info("Entering");
		var ustDto= userService.login(userName,password);
		logger.info("Leveing");
		return ResponseEntity.ok(new ApiResponseDto("Sucess", ustDto, 200, null));	
	}

}
