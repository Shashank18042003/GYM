package com.gym_membership.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym_membership.dto.request.CashPaymentRequest;
import com.gym_membership.dto.request.CreateOrderRequest;
import com.gym_membership.dto.request.VerifyPaymentRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.CreateOrderResponse;
import com.gym_membership.dto.response.PaymentHistoryResponse;
import com.gym_membership.dto.response.PaymentResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.MemberStatus;
import com.gym_membership.enums.PaymentMethod;
import com.gym_membership.enums.PaymentStatus;
import com.gym_membership.exceptions.MembershipException;
import com.gym_membership.exceptions.PaymentException;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.mapper.PaymentMapper;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.repositories.MembershipPlanRepo;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.repositories.PaymentRepository;
import com.gym_membership.services.MembershipService;
import com.gym_membership.services.PaymentService;
import com.gym_membership.services.RazorpayService;
import com.gym_membership.util.PaymentReferenceGenerator;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
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

	    private final RazorpayService razorpayService;
	    
	    private final MembershipService membershipService;
	
	
	@Override
	@Transactional
	public ApiResponse<?> createRazorpayOrder(Long memberId, CreateOrderRequest request) {
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

	    // Generate Payment Reference
	    String paymentReference = PaymentReferenceGenerator.generate();

	    // Create Razorpay Order
	    CreateOrderResponse orderResponse =
	            razorpayService.createOrder(
	                    membershipPlan,
	                    paymentReference);

	    // Save Payment
	    Payment payment = Payment.builder()
	            .member(member)
	            .membershipPlan(membershipPlan)
	            .planName(membershipPlan.getPlanName())
	            .amount(membershipPlan.getPrice())
	            .paymentMethod(PaymentMethod.RAZORPAY)
	            .paymentStatus(PaymentStatus.PENDING)
	            .paymentReference(paymentReference)
	            .razorpayOrderId(orderResponse.getOrderId())
	            .build();

	    paymentRepository.save(payment);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Razorpay order created successfully.")
	            .data(orderResponse)
	            .timestamp(LocalDateTime.now())
	            .build();
		
	}

	/**
	 * Receive VerifyPaymentRequest
            │
            ▼
	   Find Payment using Razorpay Order Id
            │
            ▼
	   Payment Exists?
            │
            No ─────► ResourceNotFoundException
            │
           Yes
            │
            ▼
	   Already SUCCESS?
            │
        	Yes ─────► PaymentException
            │
           No
            │
            ▼
	  Verify Signature
            │
       	 Invalid ───► FAILED
            │
       	  Valid
            │
            ▼
	  Update Payment
		(SUCCESS)
            │
            ▼
	  Create Membership
            │
            ▼
	  Return PaymentResponse
	 */
	@Override
	@Transactional
	public ApiResponse<?> verifyPayment(VerifyPaymentRequest request) {
		// Fetch Payment
	    Payment payment = paymentRepository
	            .findByRazorpayOrderId(request.getRazorpayOrderId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Payment not found."));

	    // Already Verified?
	    if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
	        throw new PaymentException("Payment is no longer pending verification.");
	    }

	    try {

	        // Verify Razorpay Signature
	        razorpayService.verifySignature(
	                request.getRazorpayOrderId(),
	                request.getRazorpayPaymentId(),
	                request.getRazorpaySignature());

	        // Update Payment
	        markPaymentAsSuccessful(payment, request);

	        // Create Membership
	        membershipService.createMembership(
	                payment.getMember(),
	                payment.getMembershipPlan(),
	                payment);

	    } catch (PaymentException ex) {

	        markPaymentAsFailed(payment);

	        throw ex;
	    }

	    PaymentResponse response =
	            paymentMapper.toResponse(payment);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Payment verified successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}
	//Helper methods 
	private void markPaymentAsSuccessful(
	        Payment payment,
	        VerifyPaymentRequest request) {

	    payment.setPaymentStatus(PaymentStatus.SUCCESS);

	    payment.setRazorpayPaymentId(
	            request.getRazorpayPaymentId());

	    payment.setRazorpaySignature(
	            request.getRazorpaySignature());

	    paymentRepository.save(payment);

	}
	private void markPaymentAsFailed(
	        Payment payment) {

	    payment.setPaymentStatus(PaymentStatus.FAILED);

	    paymentRepository.save(payment);

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
	@Transactional(readOnly = true)
	public ApiResponse<?> getMyPaymentHistory(Long memberId) {

	    // Fetch Member
	    Member member = memberRepository.findById(memberId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Member not found."));

	    // Fetch Payments
	    List<Payment> payments = paymentRepository
	            .findByMemberOrderByCreatedAtDesc(member);

	    // Map Payments
	    List<PaymentHistoryResponse> response = payments.stream()
	            .map(payment -> paymentMapper.toHistoryResponse(
	                    payment,
	                    getMembership(payment)))
	            .toList();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Payment history fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}
	
	private Membership getMembership(Payment payment) {

	    return membershipRepository
	            .findByPayment(payment)
	            .orElse(null);

	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<?> getAllSuccessfulPayments() {

	    // Fetch Successful Payments
	    List<Payment> payments = paymentRepository
	            .findAllByPaymentStatusOrderByCreatedAtDesc(
	                    PaymentStatus.SUCCESS);

	    // Map Payments
	    List<PaymentHistoryResponse> response = payments.stream()
	            .map(payment -> {

	                Membership membership = membershipRepository
	                        .findByPayment(payment)
	                        .orElse(null);

	                PaymentHistoryResponse dto =
	                        paymentMapper.toHistoryResponse(
	                                payment,
	                                membership);

	                // Populate Member Name
	                dto.setMemberName(
	                        payment.getMember().getFullName());

	                return dto;

	            })
	            .toList();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Successful payments fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}
	

}
