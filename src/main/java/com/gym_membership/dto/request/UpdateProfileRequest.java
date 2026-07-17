package com.gym_membership.dto.request;

import java.time.LocalDate;

import com.gym_membership.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "full name is required")
    private String fullName;

//    @Pattern(
//            regexp = "^[6-9]\\d{9}$",
//            message = "Invalid Phone Number")
//    private String phone;

    private Gender gender;
    
    private Integer age;

    private LocalDate dob;

    private Double height;

    private Double weight;

    private String address;
}