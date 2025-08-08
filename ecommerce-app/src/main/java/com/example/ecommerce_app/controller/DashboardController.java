package com.example.ecommerce_app.controller;

import java.util.List;

import org.hibernate.sql.ast.tree.expression.Literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_app.dto.ApiResponseDto;
import com.example.ecommerce_app.dto.PurchaseSummaryDto;
import com.example.ecommerce_app.service.DashboardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);


	@Autowired
    private DashboardService dashboardService;

    @GetMapping("/purchases")
    public ResponseEntity<ApiResponseDto> getPurchases(@Valid @RequestParam Long userId, @RequestParam int months,@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "0") int page ) {
    	logger.info("Entering");
    	var summary = dashboardService.getPurchaseSummary(userId, months ,size,page);
    	logger.info("Leveing");
        return ResponseEntity.ok(new ApiResponseDto("Sucess", "Purchases Details of User", 200, summary));
        }

}
