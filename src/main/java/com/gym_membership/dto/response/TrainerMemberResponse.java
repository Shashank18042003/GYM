package com.gym_membership.dto.response;

import java.time.LocalDate;

import com.gym_membership.enums.MembershipStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerMemberResponse {

    private Long memberId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String currentPlan;

    private MembershipStatus membershipStatus;

    private LocalDate expiryDate;

    private Long daysLeft;
}
