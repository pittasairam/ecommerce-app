package com.example.ecommerce_app.dto;

public class ApiResponseDto {
	
	private String message;
	private String status;
	private int code;
	private Object data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ApiResponseDto(String message, String status, int code, Object data) {
		super();
		this.message = message;
		this.status = status;
		this.code = code;
		this.data = data;
	}
	

}
