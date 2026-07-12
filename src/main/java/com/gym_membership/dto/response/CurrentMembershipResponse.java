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
public class CurrentMembershipResponse {

    private String planName;

    private MembershipStatus status;

    private LocalDate startDate;

    private LocalDate expiryDate;

}
