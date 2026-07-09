package com.gym_membership.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.services.MembershipService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

	/**
	 * Find Latest Membership
	 * 
	 * ↓
	 * 
	 * No Membership?
	 * 
	 * ↓
	 * 
	 * ACTIVE
	 * 
	 * ↓
	 * 
	 * Else
	 * 
	 * ↓
	 * 
	 * Append Membership
	 * 
	 * ↓
	 * 
	 * PENDING
	 */
	
	
	private final MembershipRepository membershipRepository;
	@Override
	@Transactional
	public Membership createMembership(Member member,
	                                   MembershipPlan membershipPlan, Payment payment) {

	    Optional<Membership> latestMembershipOptional =
	            membershipRepository.findFirstByMemberOrderByExpiryDateDesc(member);

	    Membership membership = Membership.builder()
	            .member(member)
	            .membershipPlan(membershipPlan)
	            .payment(payment)
	            .build();

	    // First Membership
	    if (latestMembershipOptional.isEmpty()) {

	        LocalDate startDate = LocalDate.now();

	        membership.setStartDate(startDate);

	        membership.setExpiryDate(
	                startDate.plusDays(
	                        membershipPlan.getDurationInDays()));

	        membership.setStatus(MembershipStatus.ACTIVE);

	    }

	    // Queue Membership
	    else {

	        Membership latestMembership = latestMembershipOptional.get();

	        LocalDate startDate =
	                latestMembership.getExpiryDate().plusDays(1);

	        membership.setStartDate(startDate);

	        membership.setExpiryDate(
	                startDate.plusDays(
	                        membershipPlan.getDurationInDays()));

	        membership.setStatus(MembershipStatus.PENDING);

	    }

	    return membershipRepository.save(membership);

	}
	@Override
	@Transactional
	public void processExpiredMemberships() {

	    List<Membership> expiredMemberships = membershipRepository
	            .findByStatusAndExpiryDate(
	                    MembershipStatus.ACTIVE,
	                    LocalDate.now());

	    if (expiredMemberships.isEmpty()) {
	        return;
	    }

	    for (Membership activeMembership : expiredMemberships) {

	        // Expire current membership
	        activeMembership.setStatus(MembershipStatus.EXPIRED);
	        membershipRepository.save(activeMembership);

	        // Find next membership in queue
	        Optional<Membership> pendingMembership = membershipRepository
	                .findFirstByMemberAndStatusOrderByStartDateAsc(
	                        activeMembership.getMember(),
	                        MembershipStatus.PENDING);

	        if (pendingMembership.isPresent()) {

	            Membership membership = pendingMembership.get();

	            membership.setStatus(MembershipStatus.ACTIVE);

	            membershipRepository.save(membership);
	        }
	    }
	}

}
