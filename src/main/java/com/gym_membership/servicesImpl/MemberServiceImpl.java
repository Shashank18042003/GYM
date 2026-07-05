package com.gym_membership.servicesImpl;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.MemberProfileResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.security.SecurityUtil;
import com.gym_membership.services.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;

    @Override
    public ApiResponse<?> getProfile() {

        String email = SecurityUtil.getLoggedInUserEmail();

        Member member = memberRepo.findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        MemberProfileResponse response = MemberProfileResponse.builder()
                .id(member.getId())
                .username(member.getUser().getUsername())
                .email(member.getUser().getEmail())
                .fullName(member.getFullName())
                .phone(member.getPhone())
                .gender(member.getGender() != null ? member.getGender().name() : null)
                .dob(member.getDob())
                .height(member.getHeight())
                .weight(member.getWeight())
                .address(member.getAddress())
                .profileImage(member.getProfileImage())
                .status(member.getStatus())
                .build();

        return ApiResponse.builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<?> updateProfile(UpdateProfileRequest request) {

        String email = SecurityUtil.getLoggedInUserEmail();

        Member member = memberRepo.findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        member.setFullName(request.getFullName());
        member.setPhone(request.getPhone());
        member.setGender(request.getGender());
        member.setDob(request.getDob());
        member.setHeight(request.getHeight());
        member.setWeight(request.getWeight());
        member.setAddress(request.getAddress());

        memberRepo.save(member);

        return ApiResponse.builder()
                .success(true)
                .message("Profile updated successfully")
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

}