package com.gym_membership.services;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.CreateOrderRequest;
import com.gym_membership.dto.request.VerifyPaymentRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface PaymentService {

    ApiResponse<?> createRazorpayOrder(Long memberId, CreateOrderRequest request);

    ApiResponse<?> verifyPayment(VerifyPaymentRequest request);

    ApiResponse<?> createCashPayment(Long memberId,
                                     CashPaymentRequest request);

    ApiResponse<?> getMyPaymentHistory(Long memberId);
    
    ApiResponse<?> getAllSuccessfulPayments();

}
