package com.gym_membership.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_membership.entity.MembershipPlan;
@Repository
public interface MembershipPlanRepo extends JpaRepository<MembershipPlan, Long>{
	
	List<MembershipPlan> findByActiveTrue();
	Optional<MembershipPlan> findByPlanName(String planName);
	boolean existsByPlanNameIgnoreCase(String planName);
}
