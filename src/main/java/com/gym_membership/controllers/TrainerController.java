package com.gym_membership.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.ChangePlanStatusRequest;
import com.gym_membership.dto.request.CreatePlanRequest;
import com.gym_membership.dto.request.UpdatePlanRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.services.MembershipPlanService;
import com.gym_membership.services.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
@Validated
public class TrainerController {

    private final MembershipPlanService membershipPlanService;
    private final PaymentService paymentService;

    /**
     * Create Membership Plan
     */
    @PostMapping("/plans")
    public ResponseEntity<ApiResponse<?>> createPlan(
            @Valid @RequestBody CreatePlanRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipPlanService.createPlan(request));
    }

    /**
     * Get All Membership Plans
     */
    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<?>> getAllPlans() {

        return ResponseEntity.ok(
                membershipPlanService.getAllPlans());
    }

    /**
     * Update Membership Plan
     */
    @PutMapping("/plans/{id}")
    public ResponseEntity<ApiResponse<?>> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlanRequest request) {

        return ResponseEntity.ok(
                membershipPlanService.updatePlan(id, request));
    }

    /**
     * Activate / Deactivate Plan
     */
    @PatchMapping("/plans/{id}/status")
    public ResponseEntity<ApiResponse<?>> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangePlanStatusRequest request) {

        return ResponseEntity.ok(
                membershipPlanService.changePlanStatus(id, request));
    }
    
    @PostMapping("/members/{memberId}/cash-payment")
    public ResponseEntity<ApiResponse<?>> createCashPayment(
            @PathVariable Long memberId,
            @Valid @RequestBody CashPaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createCashPayment(memberId, request));
    }

}