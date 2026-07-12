package com.gym_membership.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.enums.PaymentMethod;
import com.gym_membership.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryResponse {

    private String paymentReference;
    
    private String memberName;

    private String planName;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String paymentMessage;
    
    private MembershipStatus membershipStatus;

    private LocalDateTime createdAt;

}
