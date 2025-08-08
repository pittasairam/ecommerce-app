package com.example.ecommerce_app.service;

import java.util.List;

import com.example.ecommerce_app.dto.PurchaseSummaryDto;

public interface DashboardService {

	List<PurchaseSummaryDto> getPurchaseSummary(Long userId, int months, int size, int page);

}
