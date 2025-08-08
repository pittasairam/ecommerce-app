package com.example.ecommerce_app.dto;

import lombok.Data;


public class ProductDto {
	private Long productId;
	private String productName;
	private String productDesc;
	private Long productQty;
	private Long productPrice;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Long getProductQty() {
		return productQty;
	}
	public void setProductQty(Long productQty) {
		this.productQty = productQty;
	}
	public Long getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}
	
}
