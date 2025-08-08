package com.example.ecommerce_app.testserviceimpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecommerce_app.dto.OrderDto;
import com.example.ecommerce_app.dto.OrderRequestDto;
import com.example.ecommerce_app.entity.Cart;
import com.example.ecommerce_app.feignclint.BankFeignClient;
import com.example.ecommerce_app.gobleexp.CustomException;
import com.example.ecommerce_app.repo.CartRepo;
import com.example.ecommerce_app.repo.OrderRepository;
import com.example.ecommerce_app.serviceImp.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BankFeignClient bankFeignClient;

    @Mock
    private CartRepo cartRepo;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testPlaceOrder_Success() throws CustomException {
        // Arrange
        var userId = 1L;
        var cartId = 101L;

        var cart = new Cart();
        cart.setCartId(cartId);
        cart.setUserId(userId);
        cart.setProductId(1001L);
        cart.setProductName("Test Product");
        cart.setQuantity(2L);
        cart.setPrice(50L);

        var requestDto = new OrderRequestDto();
        requestDto.setUserId(userId);
        requestDto.setCartId(cartId);
        requestDto.setFromAccount(1234567890L);

        when(cartRepo.findByUserId(userId)).thenReturn(List.of(cart));
        when(cartRepo.findByCartId(cartId)).thenReturn(Optional.of(cart));

        // Act
        var result = orderService.placeOrder(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(100.0, result.getTotalAmount());
        assertEquals(1, result.getItems().size());
        assertEquals("Test Product", result.getItems().get(0).getProductName());
        LocalDateTime localDateTime= LocalDateTime.now();

        verify(bankFeignClient).doTransaction(eq(1234567890L), eq(9999999999L), eq(BigDecimal.valueOf(100.0)));
        verify(orderRepository).saveOrders(localDateTime, (double) 100L, userId);
        verify(cartRepo).deleteByProductId(1001L);
    }

    @Test
    public void testPlaceOrder_CartNotFound_ThrowsException() {
        // Arrange
        var requestDto = new OrderRequestDto();
        requestDto.setUserId(1L);
        requestDto.setCartId(101L);

        when(cartRepo.findByUserId(1L)).thenReturn(List.of());
        when(cartRepo.findByCartId(101L)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(CustomException.class, () -> {
            orderService.placeOrder(requestDto);
        });

        assertEquals("Item is not available in cart", exception.getMessage());
    }

    @Test
    public void testPlaceOrder_TransactionFails_ThrowsException() {
        // Arrange
        var userId = 1L;
        var cartId = 101L;

        var cart = new Cart();
        cart.setCartId(cartId);
        cart.setUserId(userId);
        cart.setProductId(1001L);
        cart.setProductName("Test Product");
        cart.setQuantity(2l);
        cart.setPrice(50l);

        var requestDto = new OrderRequestDto();
        requestDto.setUserId(userId);
        requestDto.setCartId(cartId);
        requestDto.setFromAccount(1234567890L);

        when(cartRepo.findByUserId(userId)).thenReturn(List.of(cart));
        when(cartRepo.findByCartId(cartId)).thenReturn(Optional.of(cart));
        doThrow(new RuntimeException("Insufficient funds")).when(bankFeignClient)
            .doTransaction(anyLong(), anyLong(), any(BigDecimal.class));

        // Act & Assert
        var exception = assertThrows(CustomException.class, () -> {
            orderService.placeOrder(requestDto);
        });

        assertEquals("Transaction failed: Insufficient Amount", exception.getMessage());
    }
}
