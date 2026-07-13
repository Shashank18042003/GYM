package com.gym_membership.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	long countByPaymentStatus(
	        PaymentStatus paymentStatus);
	@Query("""
			SELECT COALESCE(SUM(p.amount),0)
			FROM Payment p
			WHERE p.paymentStatus='SUCCESS'
			AND DATE(p.createdAt)=CURRENT_DATE
			""")
			BigDecimal getTodayRevenue();
	
	@Query("""
			SELECT COALESCE(SUM(p.amount),0)
			FROM Payment p
			WHERE p.paymentStatus='SUCCESS'
			AND YEAR(p.createdAt)=YEAR(CURRENT_DATE)
			AND MONTH(p.createdAt)=MONTH(CURRENT_DATE)
			""")
			BigDecimal getMonthlyRevenue();

}
