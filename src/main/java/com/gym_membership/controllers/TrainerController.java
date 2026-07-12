package com.gym_membership.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.ChangePlanStatusRequest;
import com.gym_membership.dto.request.CreateEventRequest;
import com.gym_membership.dto.request.CreatePlanRequest;
import com.gym_membership.dto.request.UpdateEventRequest;
import com.gym_membership.dto.request.UpdatePlanRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.enums.MemberFilter;
import com.gym_membership.services.EventService;
import com.gym_membership.services.MemberService;
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
    private final MemberService memberService;
    private final EventService eventService;

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
    
    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<?>> getAllSuccessfulPayments() {

        return ResponseEntity.ok(
                paymentService.getAllSuccessfulPayments());
    }
   
    @GetMapping("/members")
    public ResponseEntity<ApiResponse<?>> getMembers(
            @RequestParam(defaultValue = "ALL") MemberFilter filter,
            @RequestParam(required = false) Integer days) {

        return ResponseEntity.ok(
                memberService.getMembers(filter, days));
    }
    
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<?>> getMemberDetails(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                memberService.getMemberDetails(memberId));
    }
    
    @PostMapping("/events")
    public ResponseEntity<ApiResponse<?>> createEvent(
            @Valid @RequestBody CreateEventRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(request));
    }
    
    @GetMapping("/events")
    public ResponseEntity<ApiResponse<?>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents());
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<?>> getEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                eventService.getEvent(eventId));
    }
    
    @PutMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<?>> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventRequest request) {

        return ResponseEntity.ok(
                eventService.updateEvent(eventId, request));
    }
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<?>> deleteEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                eventService.deleteEvent(eventId));
    }
    
    
  //  private final MembershipService membershipService;

//    @PostMapping("/scheduler")
//    public String runScheduler() {
//
//        membershipService.processExpiredMemberships();
//
//        return "Scheduler Executed Successfully";
//    }

}