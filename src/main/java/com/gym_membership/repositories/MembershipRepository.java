package com.gym_membership.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.MembershipStatus;

public interface MembershipRepository extends JpaRepository<Membership, Long>{
	
	/**Returns

	Latest Membership

	ACTIVE or PENDING

	Whichever expires last.**/
	Optional<Membership> findFirstByMemberOrderByExpiryDateDesc(Member member);
	
	/**
	 * Returns
	 * 
	 * First Pending Membership
	 * 
	 * for Scheduler
	 */
	Optional<Membership> findFirstByMemberAndStatusOrderByStartDateAsc(
	        Member member,
	        MembershipStatus status);
	
	/**
	 * Scheduler
	 * 
	 * ↓
	 * 
	 * Find expired ACTIVE memberships
	 */
	List<Membership> findByStatusAndExpiryDate(
	        MembershipStatus status,
	        LocalDate expiryDate);
	
	
	Optional<Membership> findByPayment(Payment payment);
	
	List<Membership> findByMemberAndStatusOrderByCreatedAtAsc(
	        Member member,
	        MembershipStatus status);

	List<Membership> findByStatusOrderByExpiryDateAsc(
	        MembershipStatus status);
	
	List<Membership> findByStatusAndExpiryDateBetweenOrderByExpiryDateAsc(
	        MembershipStatus status,
	        LocalDate startDate,
	        LocalDate endDate);
	
	long countByStatus(MembershipStatus status);
	long countByStatusAndExpiryDateBetween(
	        MembershipStatus status,
	        LocalDate startDate,
	        LocalDate endDate);
	
	// previous ones
	/**
	Optional<Membership> findFirstByMemberAndStatusOrderByExpiryDateDesc(Member member,MembershipStatus status);
	List<Membership> findByMemberOrderByCreatedAtDesc(Member member);
**/
}
