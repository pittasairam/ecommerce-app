package com.example.ecommerce_app.testcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ecommerce_app.controller.OrderController;
import com.example.ecommerce_app.dto.OrderDto;
import com.example.ecommerce_app.dto.OrderItemDto;
import com.example.ecommerce_app.dto.OrderRequestDto;
import com.example.ecommerce_app.service.OrderService;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testPlaceOrder() throws Exception {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setProductId(1L);
        itemDto.setProductName("Laptop");
        itemDto.setQuantity(2);
        itemDto.setPrice(50000.0);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setUserId(101L);
        requestDto.setItems(List.of(itemDto));

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1L);
        orderDto.setUserId(101L);
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setTotalAmount(100000.0);
        orderDto.setItems(List.of(itemDto));

        when(orderService.placeOrder(any(OrderRequestDto.class))).thenReturn(orderDto);

		
    }
}
