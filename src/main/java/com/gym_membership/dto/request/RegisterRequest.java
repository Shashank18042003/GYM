package com.gym_membership.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank(message="Username is required")
	@Size(min = 4, max = 20)
	private String username;
	
	@NotBlank(message = "Full name is required")
	private String fullname;
	
	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	private String email;
	
	@Pattern(regexp="^[6-9]\\d{9}$",message = "Phone number must be 10 digits")
	private String phone;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6,message = "Password should be at least 8 characters")
	private String password;
	

}
