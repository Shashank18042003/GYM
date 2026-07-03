package com.gym_membership.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym_membership.dto.request.LoginRequest;
import com.gym_membership.dto.request.RegisterRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {
	
	private final AuthService authService;
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<?>> register(
			@Valid @RequestBody RegisterRequest request){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<?>> login(
			@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.login(request));
	}
}
