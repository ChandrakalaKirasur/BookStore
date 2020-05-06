package com.bridgelabz.bookstoreapi.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstoreapi.exception.AdminException;
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.response.ExceptionResponse;



@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(UserException ex) {
		
		return ResponseEntity.status(ex.getErrorCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getErrorMessage()));
	
	}
	
	@ExceptionHandler(BookException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(BookException ex) {
		
		return ResponseEntity.status(ex.getErrorCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getErrorMsg()));
	
	}
	@ExceptionHandler(AdminException.class)
	public final ResponseEntity<ExceptionResponse> handlerAdminException(AdminException ex) {
		
		return ResponseEntity.status(ex.getErrorCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getErrorMessage()));
	
	}
	@ExceptionHandler(SellerException.class)
	public final ResponseEntity<ExceptionResponse> handlerAdminException(SellerException ex) {
		
		return ResponseEntity.status(ex.getErrorCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getErrorMsg()));
	
	}
}
