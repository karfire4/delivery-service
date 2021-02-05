package com.org.startup.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.org.startup.dto.exception.ExceptionResponse;

@RestControllerAdvice 
public class GlobalHandler {
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ExceptionResponse> handler(InvalidDataException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.BAD_REQUEST.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handler(Exception ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.INTERNAL_SERVER_ERROR.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
}
