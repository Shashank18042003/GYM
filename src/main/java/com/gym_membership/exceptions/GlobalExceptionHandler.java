package com.gym_membership.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gym_membership.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExists(UserAlreadyExistsException ex){
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(PhoneAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException ex){
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex){
		
		String errorMessage=ex.getBindingResult()
				.getFieldError()
				.getDefaultMessage();
		
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){
		ApiResponse<Object> response=ApiResponse.builder()
			.success(false)
			.message(ex.getMessage())
			.data(null)
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
	        BadCredentialsException ex){

	    ApiResponse<Object> response = ApiResponse.builder()
	            .success(false)
	            .message("Invalid username or password")
	            .data(null)
	            .timestamp(LocalDateTime.now())
	            .build();

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body(response);
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ApiResponse<Object>> handleFileStorageException(
	        FileStorageException ex) {

	    ApiResponse<Object> response = ApiResponse.builder()
	            .success(false)
	            .message(ex.getMessage())
	            .data(null)
	            .timestamp(LocalDateTime.now())
	            .build();

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(response);
	}

}
