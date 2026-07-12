package com.gym_membership.mapper;

import org.springframework.stereotype.Component;

import com.gym_membership.dto.response.TrainerMemberResponse;
import com.gym_membership.entity.Member;

@Component
public class MemberMapper {
	
    public TrainerMemberResponse toTrainerResponse(Member member) {

        return TrainerMemberResponse.builder()
                .memberId(member.getId())
                .fullName(member.getFullName())
                .email(member.getUser().getEmail())
                .phoneNumber(member.getPhone())
                .build();
    }

}
