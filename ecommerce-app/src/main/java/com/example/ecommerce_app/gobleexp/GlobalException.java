package com.example.ecommerce_app.gobleexp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ecommerce_app.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex){
		var error=new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.NOT_FOUND);
	}
 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleAllExceptions(MethodArgumentNotValidException ex){
		Map<String,String> errors=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(e->{errors.put(e.getField(), e.getDefaultMessage());
	    });
		return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
	}

}
