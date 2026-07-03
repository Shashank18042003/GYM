package com.gym_membership.services;

import com.gym_membership.dto.request.LoginRequest;
import com.gym_membership.dto.request.RegisterRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface AuthService {
	ApiResponse<?> register(RegisterRequest request);
	ApiResponse<?> login(LoginRequest request);
}
