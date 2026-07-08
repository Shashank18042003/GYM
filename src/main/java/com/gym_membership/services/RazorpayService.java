package com.gym_membership.services;

import com.gym_membership.dto.response.CreateOrderResponse;

public interface RazorpayService {

    CreateOrderResponse createOrder(Long amount);

    boolean verifySignature(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature);

}
