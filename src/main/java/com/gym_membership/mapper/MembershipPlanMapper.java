package com.gym_membership.mapper;

import org.springframework.stereotype.Component;

import com.gym_membership.dto.response.MembershipPlanResponse;
import com.gym_membership.entity.MembershipPlan;

@Component
public class MembershipPlanMapper {

    public MembershipPlanResponse toResponse(MembershipPlan plan) {

        if(plan==null)
            return null;

        return MembershipPlanResponse.builder()
                .id(plan.getId())
                .planName(plan.getPlanName())
                .durationInDays(plan.getDurationInDays())
                .price(plan.getPrice())
                .description(plan.getDescription())
                .active(plan.getActive())
                .build();

    }

}