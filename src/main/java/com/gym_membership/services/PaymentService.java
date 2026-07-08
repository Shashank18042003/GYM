package com.gym_membership.services;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.CreateOrderRequest;
import com.gym_membership.dto.request.VerifyPaymentRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface PaymentService {

    ApiResponse<?> createRazorpayOrder(CreateOrderRequest request);

    ApiResponse<?> verifyPayment(VerifyPaymentRequest request);

    ApiResponse<?> createCashPayment(Long memberId,
                                     CashPaymentRequest request);

    ApiResponse<?> getPaymentHistory();

}
