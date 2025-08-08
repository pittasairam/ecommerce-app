package com.example.ecommerce_app.testcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ecommerce_app.controller.UserController;
import com.example.ecommerce_app.dto.UserDto;
import com.example.ecommerce_app.service.UserService;



@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    

    @Test
    void testRegisterUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPhoneNumber(9898745453l);

        when(userService.register(any(UserDto.class))).thenReturn(userDto);

    }

    @Test
    void testLoginUser() throws Exception {
        when(userService.login("testUser", "password")).thenReturn("login Sucess");

    }
}
