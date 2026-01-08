package com.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.model.user_model;
import com.user.payload.ApiResponse;

import lombok.Builder;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
		@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity <ApiResponse>handlerResourceNotFoundException(ResourceNotFoundException ex){
		String message=ex.getMessage();
		ApiResponse response=ApiResponse.builder().message(message).success(false).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
	}

}
