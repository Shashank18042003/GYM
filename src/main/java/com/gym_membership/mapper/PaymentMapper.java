package com.gym_membership.mapper;

import org.springframework.stereotype.Component;

import com.gym_membership.dto.response.PaymentHistoryResponse;
import com.gym_membership.dto.response.PaymentResponse;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.Payment;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {

        if (payment == null) {
            return null;
        }

        return PaymentResponse.builder()
                .paymentReference(payment.getPaymentReference())
                .planName(payment.getPlanName())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getCreatedAt())
                .build();
    }
    
    public PaymentHistoryResponse toHistoryResponse(
	        Payment payment,
	        Membership membership) {

	    return PaymentHistoryResponse.builder()
	            .paymentReference(payment.getPaymentReference())
	            .planName(payment.getPlanName())
	            .amount(payment.getAmount())
	            .paymentMethod(payment.getPaymentMethod())
	            .paymentStatus(payment.getPaymentStatus())
	            .paymentMessage(payment.getPaymentMessage())
	            .membershipStatus(
	                    membership != null
	                            ? membership.getStatus()
	                            : null)
	            .createdAt(payment.getCreatedAt())
	            .build();
	}

}