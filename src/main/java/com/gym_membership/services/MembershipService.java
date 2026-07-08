package com.gym_membership.services;

import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.entity.Payment;

public interface MembershipService {
	
	Membership activateMembership(Member member,
            MembershipPlan membershipPlan,
            Payment payment);

}
