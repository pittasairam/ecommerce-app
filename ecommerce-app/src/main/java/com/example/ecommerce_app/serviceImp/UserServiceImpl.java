package com.example.ecommerce_app.serviceImp;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.controller.OrderController;
import com.example.ecommerce_app.dto.UserDto;
import com.example.ecommerce_app.entity.User;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.repo.UserRepo;
import com.example.ecommerce_app.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDto register(UserDto userDto) throws CustomException {
		logger.info("Entering");
		var user=new User();
		var userExitst= userRepo.findByEmailOrPhoneNumber(userDto.getEmail(),userDto.getPhoneNumber());
		
		if(userExitst.isPresent()) {
			throw new CustomException("Email or Phone Number is alredy Exiest");
		}
		BeanUtils.copyProperties(userDto, user);
		userRepo.save(user);
		BeanUtils.copyProperties(user, userDto);
		logger.info("Leveing");

		return userDto;
	}

	@Override
	public String login(String userName, String Password) throws CustomException{
		logger.info("Entering");
		var userExitst= userRepo.findByUserNameAndPassword(userName,Password);
		if(!userExitst.isPresent()) {
			throw new CustomException("User details are Not Present Register the User first");
		}
		logger.info("Leveing");
		return "login Sucess";
	}
	
	

}
