package com.gym_membership.dto.response;

import java.time.LocalDate;

import com.gym_membership.enums.Gender;
import com.gym_membership.enums.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileResponse {

    private Long id;

    private String username;

    private Integer age;
    private String email;

    private String fullName;

    private String phone;

    private Gender gender;

    private LocalDate dob;

    private Double height;

    private Double weight;

    private String address;

    private String profileImage;

    private MemberStatus status;
}
