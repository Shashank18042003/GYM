package com.gym_membership.dto.response;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gym_membership.enums.PaymentMethod;
import com.gym_membership.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String paymentReference;

    private String planName;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

}