package com.gym_membership.servicesImpl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym_membership.dto.request.LoginRequest;
import com.gym_membership.dto.request.RegisterRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.JwtResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.User;
import com.gym_membership.enums.MemberStatus;
import com.gym_membership.enums.Role;
import com.gym_membership.exceptions.EmailAlreadyExistsException;
import com.gym_membership.exceptions.PhoneAlreadyExistsException;
import com.gym_membership.exceptions.UserAlreadyExistsException;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.repositories.UserRepo;
import com.gym_membership.security.JwtService;
import com.gym_membership.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepo userRepo;
	private final MemberRepo memberRepo;
	private final PasswordEncoder passwordEncoder;
	@Override
	public ApiResponse<?> register(RegisterRequest request) {
		if(userRepo.existsByUsername(request.getUsername())) {
			throw new UserAlreadyExistsException("Username already exists");
		}
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		if(memberRepo.existsByPhone(request.getPhone())) {
			throw new PhoneAlreadyExistsException("This Phone number already exists, Kindly choose different number");
		}
		User user=User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_MEMBER)
				.enabled(true)
				.build();
		
		User savedUser=userRepo.save(user);
		
		Member member=Member.builder()
				.user(savedUser)
				.fullName(request.getFullname())
				.phone(request.getPhone())
				.status(MemberStatus.ACTIVE)
				.build();
		memberRepo.save(member);
		
		return ApiResponse.builder()
				.success(true)
				.message("Member Registered Successfully")
				.data(null)
				.timestamp(LocalDateTime.now())
				.build();
	}
	
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	@Override
	public ApiResponse<?> login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		User user=userRepo.findByEmail(request.getEmail())
				.orElseThrow(()->new UsernameNotFoundException("Invalid username or password"));
		String token=jwtService.generateToken(user);
		
		JwtResponse response=JwtResponse.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.role(user.getRole().name())
				.token(token)
				.build();
		
		
		return ApiResponse.builder()
				.success(true)
				.message("Login Successful")
				.data(response)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
