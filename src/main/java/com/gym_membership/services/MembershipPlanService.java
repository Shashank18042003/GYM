package com.gym_membership.services;

import com.gym_membership.dto.request.ChangePlanStatusRequest;
import com.gym_membership.dto.request.CreatePlanRequest;
import com.gym_membership.dto.request.UpdatePlanRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface MembershipPlanService {
	
	 	ApiResponse<?> createPlan(CreatePlanRequest request);

	  	ApiResponse<?> getAllPlans();

	    ApiResponse<?> getActivePlans();

	    ApiResponse<?> updatePlan(Long id, UpdatePlanRequest request);

	    ApiResponse<?> changePlanStatus(Long id, ChangePlanStatusRequest request);
}
