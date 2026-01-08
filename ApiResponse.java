package com.user.payload;

import org.springframework.http.HttpStatus;

import lombok.Builder;
@Builder
public class ApiResponse {

    private String message;
    private boolean success;
    private HttpStatus status;
	

}