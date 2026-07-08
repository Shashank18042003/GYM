package com.gym_membership.servicesImpl;


import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.ImageUploadResponse;
import com.gym_membership.dto.response.MemberProfileResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.entity.Payment;
import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.security.SecurityUtil;
import com.gym_membership.services.MemberService;
import com.gym_membership.storage.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;
    private final FileStorageService fileStorageService;
    private final MembershipRepository membershipRepo;

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

	@Override
	public ApiResponse<?> uploadProfilePicture(MultipartFile file) {
		String email = SecurityUtil.getLoggedInUserEmail();

	    Member member = memberRepo.findByUserEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Member not found"));

	    String fileName = fileStorageService.uploadProfileImage(file);

	    member.setProfileImage(fileName);

	    memberRepo.save(member);

	    ImageUploadResponse response = ImageUploadResponse.builder()
	            .imageUrl("/uploads/profile/" + fileName)
	            .build();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Profile picture uploaded successfully")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}


}