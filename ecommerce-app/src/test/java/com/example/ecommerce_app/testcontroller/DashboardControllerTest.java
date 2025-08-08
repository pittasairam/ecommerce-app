package com.example.ecommerce_app.testcontroller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ecommerce_app.controller.DashboardController;
import com.example.ecommerce_app.dto.PurchaseSummaryDto;
import com.example.ecommerce_app.service.DashboardService;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    void testGetPurchases() throws Exception {
        PurchaseSummaryDto dto = new PurchaseSummaryDto();
        dto.setOrderId(1L);
        dto.setTotalAmount(1500.0);
        dto.setOrderDate(LocalDateTime.now());

        List<PurchaseSummaryDto> summaryList = List.of(dto);

        when(dashboardService.getPurchaseSummary(101L, 3, 5, 1)).thenReturn(summaryList);
        

    }
}
