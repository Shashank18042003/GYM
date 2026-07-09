package com.gym_membership.servicesImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.CreateOrderRequest;
import com.gym_membership.dto.request.VerifyPaymentRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.PaymentResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.MemberStatus;
import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.enums.PaymentMethod;
import com.gym_membership.enums.PaymentStatus;
import com.gym_membership.exceptions.MembershipException;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.mapper.PaymentMapper;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.repositories.MembershipPlanRepo;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.repositories.PaymentRepository;
import com.gym_membership.services.MembershipService;
import com.gym_membership.services.PaymentService;
import com.gym_membership.util.PaymentReferenceGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
/**
    MEMBER
    │
    ▼
Select Membership Plan
    │
    ▼
Payment Module
┌───────────────┐
│               │
▼               ▼
CASH          RAZORPAY
│               │
▼               ▼
SUCCESS       VERIFY PAYMENT
│               │
└───────┬───────┘
   ▼
activateMembership()
   │
   ▼
Membership ACTIVE Table Updated
	**/
	
	
	    private final PaymentRepository paymentRepository;

	    private final MembershipRepository membershipRepository;

	    private final MembershipPlanRepo membershipPlanRepository;

	    private final MemberRepo memberRepository;

	    private final PaymentMapper paymentMapper;

	    //private final RazorpayService razorpayService;
	    
	    private final MembershipService membershipService;
	
	
	@Override
	
	public ApiResponse<?> createRazorpayOrder(CreateOrderRequest request) {
		return null;
		
	}

	@Override
	public ApiResponse<?> verifyPayment(VerifyPaymentRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public ApiResponse<?> createCashPayment(Long memberId, CashPaymentRequest request) {

	    // Fetch Member
	    Member member = memberRepository.findById(memberId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Member not found."));

	    // Validate Member Status
	    if (member.getStatus() != MemberStatus.ACTIVE) {
	        throw new MembershipException(
	                "Only active members can purchase a membership.");
	    }

	    // Fetch Membership Plan
	    MembershipPlan membershipPlan = membershipPlanRepository
	            .findById(request.getPlanId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Membership plan not found."));

	    // Validate Plan Status
	    if (!Boolean.TRUE.equals(membershipPlan.getActive())) {
	        throw new MembershipException(
	                "Selected membership plan is currently inactive.");
	    }

	    // Create Payment
	    Payment payment = Payment.builder()
	            .member(member)
	            .membershipPlan(membershipPlan)
	            .planName(membershipPlan.getPlanName())
	            .amount(membershipPlan.getPrice())
	            .paymentMethod(PaymentMethod.CASH)
	            .paymentStatus(PaymentStatus.SUCCESS)
	            .paymentReference(PaymentReferenceGenerator.generate())
	            .build();

	    payment = paymentRepository.save(payment);

	    // Create Membership (ACTIVE or PENDING based on queue)
	    membershipService.createMembership(
	            member,
	            membershipPlan,payment);

	    // Build Response
	    PaymentResponse response = paymentMapper.toResponse(payment);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Cash payment recorded successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	public ApiResponse<?> getPaymentHistory() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
