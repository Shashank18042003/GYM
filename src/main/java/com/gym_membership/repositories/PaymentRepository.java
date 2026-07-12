package com.gym_membership.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_membership.entity.Member;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	
	Optional<Payment> findByPaymentReference(String paymentReference);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    List<Payment> findByMemberOrderByCreatedAtDesc(Member member);
    

	List<Payment> findAllByPaymentStatusOrderByCreatedAtDesc(PaymentStatus success);

}
