package com.gym_membership.servicesImpl;

import java.time.LocalDate;

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
@Transactional
public class MembershipServiceImpl implements MembershipService {

	private final MembershipRepository membershipRepository;
	@Override
	public Membership activateMembership(Member member, MembershipPlan membershipPlan, Payment payment) {
		Membership membership = Membership.builder()
                .member(member)
                .membershipPlan(membershipPlan)
                .startDate(LocalDate.now())
                .expiryDate(LocalDate.now()
                        .plusDays(membershipPlan.getDurationInDays()))
                .status(MembershipStatus.ACTIVE)
                .build();

        return membershipRepository.save(membership);
	}

}
