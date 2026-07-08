package com.gym_membership.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.enums.MembershipStatus;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long>{
	
	Optional<Membership> findFirstByMemberAndStatusOrderByExpiryDateDesc(Member member,MembershipStatus status);
	List<Membership> findByMemberOrderByCreatedAtDesc(Member member);

}
