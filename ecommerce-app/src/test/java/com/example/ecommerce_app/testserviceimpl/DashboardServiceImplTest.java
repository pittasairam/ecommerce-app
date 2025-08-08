package com.example.ecommerce_app.testserviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.ecommerce_app.dto.PurchaseSummaryDto;
import com.example.ecommerce_app.entity.Order;
import com.example.ecommerce_app.repo.OrderRepository;
import com.example.ecommerce_app.serviceImp.DashboardServiceImpl;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testGetPurchaseSummary() {
        Order order = new Order();
        order.setId(1L);
        order.setUserId(101L);
        order.setOrderDate(LocalDateTime.now().minusDays(10));
        order.setTotalAmount(1500.0);

        Pageable pageable = PageRequest.of(0, 5);
        List<Order> orders = List.of(order);

        when(orderRepository.findByUserIdAndOrderDateAfter(eq(101L), any(LocalDateTime.class), eq(pageable)))
                .thenReturn(orders);

        List<PurchaseSummaryDto> result = dashboardService.getPurchaseSummary(101L, 3, 5, 0);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(1500.0, result.get(0).getTotalAmount());
    }
}
