package com.example.ecommerce_app.testserviceimpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecommerce_app.dto.UserDto;
import com.example.ecommerce_app.entity.User;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.repo.UserRepo;
import com.example.ecommerce_app.serviceImp.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestImpl{
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepo userRepo;

	@Test
	void testRegisterSuccess() throws CustomException {
		UserDto userDto = new UserDto();
		userDto.setEmail("test@example.com");
		userDto.setPhoneNumber(1234567890l);

		when(userRepo.findByEmailOrPhoneNumber(anyString(), anyLong())).thenReturn(Optional.empty());

		when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

		UserDto result = userService.register(userDto);

		assertEquals("test@example.com", result.getEmail());
	}

	@Test
	void testRegisterThrowsException() {
		UserDto userDto = new UserDto();
		userDto.setEmail("test@example.com");
		userDto.setPhoneNumber(1234567890l);

		when(userRepo.findByEmailOrPhoneNumber(anyString(),  anyLong())).thenReturn(Optional.of(new User()));

		assertThrows(CustomException.class, () -> userService.register(userDto));
	}

	@Test
	    void testLoginSuccess() throws CustomException {
	        when(userRepo.findByUserNameAndPassword("testUser", "password"))
	                .thenReturn(Optional.of(new User()));

	        String result = userService.login("testUser", "password");

	        assertEquals("login Sucess", result);
	    }

	@Test
	    void testLoginThrowsException() {
	        when(userRepo.findByUserNameAndPassword("testUser", "password"))
	                .thenReturn(Optional.empty());

	        assertThrows(CustomException.class, () -> userService.login("testUser", "password"));
	    }

}
