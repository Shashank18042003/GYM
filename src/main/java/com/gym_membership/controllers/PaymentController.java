package com.gym_membership.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym_membership.dto.request.CreateOrderRequest;
import com.gym_membership.dto.request.VerifyPaymentRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.services.PaymentService;
import com.gym_membership.util.LoggedInUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;
    private final LoggedInUserService loggedInUserService;

    /**
     * Member creates a Razorpay order.
     */
    @PostMapping("/payments/create-order")
    public ResponseEntity<ApiResponse<?>> createOrder(
            Authentication authentication,
            @Valid @RequestBody CreateOrderRequest request) {

    	Member member = loggedInUserService.getLoggedInMember(authentication);

        return ResponseEntity.ok(
                paymentService.createRazorpayOrder(
                        member.getId(),
                        request));
    }

    /**
     * Member verifies Razorpay payment after successful checkout.
     */
    @PostMapping("/payments/verify")
    public ResponseEntity<ApiResponse<?>> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(request));
    }
    
    @GetMapping("/payments/history")
    public ResponseEntity<ApiResponse<?>> getMyPaymentHistory(
            Authentication authentication) {

        Member member = loggedInUserService
                .getLoggedInMember(authentication);

        return ResponseEntity.ok(
                paymentService.getMyPaymentHistory(member.getId()));
    }

}