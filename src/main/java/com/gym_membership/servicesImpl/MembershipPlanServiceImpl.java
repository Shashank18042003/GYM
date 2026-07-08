package com.gym_membership.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gym_membership.dto.request.ChangePlanStatusRequest;
import com.gym_membership.dto.request.CreatePlanRequest;
import com.gym_membership.dto.request.UpdatePlanRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.MembershipPlanResponse;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.exceptions.PlanAlreadyExistsException;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.mapper.MembershipPlanMapper;
import com.gym_membership.repositories.MembershipPlanRepo;
import com.gym_membership.services.MembershipPlanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {

	private final MembershipPlanRepo membershipPlanRepo;
	private final MembershipPlanMapper membershipPlanMapper;
	
	@Override
	@Transactional
	public ApiResponse<?> createPlan(CreatePlanRequest request) {
		 if (membershipPlanRepo.existsByPlanNameIgnoreCase(request.getPlanName())) {
	            throw new PlanAlreadyExistsException("Membership plan already exists.");
	        }

	        MembershipPlan membershipPlan = MembershipPlan.builder()
	                .planName(request.getPlanName())
	                .durationInDays(request.getDurationInDays())
	                .price(request.getPrice())
	                .description(request.getDescription())
	                .active(true)
	                .build();

	        membershipPlanRepo.save(membershipPlan);

	        return buildResponse(
	                "Membership plan created successfully.",
	                membershipPlanMapper.toResponse(membershipPlan));
	    
	}

	@Override
	public ApiResponse<?> getAllPlans() {
		List<MembershipPlanResponse> response = membershipPlanRepo
                .findAll()
                .stream()
                .map(membershipPlanMapper::toResponse)
                .toList();

        return buildResponse(
                "Membership plans fetched successfully.",
                response);
	}

	@Override
	public ApiResponse<?> getActivePlans() {
		List<MembershipPlanResponse> response = membershipPlanRepo
                .findByActiveTrue()
                .stream()
                .map(membershipPlanMapper::toResponse)
                .toList();

        return buildResponse(
                "Active membership plans fetched successfully.",
                response);
	}

	@Override
	@Transactional
	public ApiResponse<?> updatePlan(Long id, UpdatePlanRequest request) {
		MembershipPlan membershipPlan = getMembershipPlan(id);

        if (!membershipPlan.getPlanName().equalsIgnoreCase(request.getPlanName())
                && membershipPlanRepo.existsByPlanNameIgnoreCase(request.getPlanName())) {

            throw new PlanAlreadyExistsException("Membership plan already exists.");
        }

        membershipPlan.setPlanName(request.getPlanName());
        membershipPlan.setDurationInDays(request.getDurationInDays());
        membershipPlan.setPrice(request.getPrice());
        membershipPlan.setDescription(request.getDescription());

        membershipPlanRepo.save(membershipPlan);

        return buildResponse(
                "Membership plan updated successfully.",
                membershipPlanMapper.toResponse(membershipPlan));
	}

	@Override
	@Transactional
	public ApiResponse<?> changePlanStatus(Long id, ChangePlanStatusRequest request) {
		 MembershipPlan membershipPlan = getMembershipPlan(id);

	        membershipPlan.setActive(request.getActive());

	        membershipPlanRepo.save(membershipPlan);

	        return buildResponse(
	                request.getActive()
	                        ? "Membership plan activated successfully."
	                        : "Membership plan deactivated successfully.",
	                membershipPlanMapper.toResponse(membershipPlan));
	}
	
	
	 private ApiResponse<Object> buildResponse(String message,
             Object data) {
		 
		 return ApiResponse.builder()
		.success(true)
		.message(message)
		.data(data)
		.timestamp(LocalDateTime.now())
		.build();
}
	 
	 private MembershipPlan getMembershipPlan(Long id) {

	        return membershipPlanRepo.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Membership plan not found."));
	 }

}
