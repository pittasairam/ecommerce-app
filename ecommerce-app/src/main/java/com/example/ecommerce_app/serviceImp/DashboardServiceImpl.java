package com.example.ecommerce_app.serviceImp;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.dto.PurchaseSummaryDto;
import com.example.ecommerce_app.repo.OrderRepository;
import com.example.ecommerce_app.service.DashboardService;
@Service
public class DashboardServiceImpl implements DashboardService{

	

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<PurchaseSummaryDto> getPurchaseSummary(Long userId, int months,int size, int page) {
    	var pageable =PageRequest.of(page, size);
        var fromDate = LocalDateTime.now().minusMonths(months);
        var orders = orderRepository.findByUserIdAndOrderDateAfter(userId, fromDate,pageable);

        return orders.stream().map(order -> {
            var dto = new PurchaseSummaryDto();
            dto.setOrderId(order.getId());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderDate(order.getOrderDate());
            return dto;
        }).toList();
    }


}
