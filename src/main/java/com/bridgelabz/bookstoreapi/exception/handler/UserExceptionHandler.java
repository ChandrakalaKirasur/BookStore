package com.bridgelabz.bookstoreapi.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.response.ExceptionResponse;



@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(UserException ex) {
		
		return ResponseEntity.status(ex.getErrorCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getErrorMessage()));
	
	}
}
