package com.gym_membership.services;

import com.gym_membership.dto.response.CreateOrderResponse;
import com.gym_membership.entity.MembershipPlan;

public interface RazorpayService {

    CreateOrderResponse createOrder(MembershipPlan membershipPlan, String paymentReference);

    boolean verifySignature(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature);

}
