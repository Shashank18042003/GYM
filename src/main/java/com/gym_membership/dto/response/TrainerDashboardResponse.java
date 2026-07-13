package com.gym_membership.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDashboardResponse {

    // Members
    private Long totalMembers;

    private Long activeMembers;

    private Long expiredMembers;

    private Long renewalDueMembers;

    // Revenue
    private BigDecimal todayRevenue;

    private BigDecimal monthlyRevenue;

    // Payments
    private Long totalSuccessfulPayments;

    // Events
    private Long totalUpcomingEvents;

}