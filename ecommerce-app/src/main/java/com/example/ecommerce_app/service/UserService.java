package com.example.ecommerce_app.service;


import com.example.ecommerce_app.dto.UserDto;
import com.example.ecommerce_app.gobleexp.CustomException;





public interface UserService {

	UserDto register( UserDto userDto) throws CustomException;

	String login(String userName, String password) throws CustomException;

}
