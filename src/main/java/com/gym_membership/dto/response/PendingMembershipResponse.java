package com.gym_membership.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gym_membership.enums.MembershipStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingMembershipResponse {

    private String planName;

    private MembershipStatus status;

    private LocalDateTime createdAt;

}