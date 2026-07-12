package com.gym_membership.servicesImpl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.CurrentMembershipResponse;
import com.gym_membership.dto.response.ImageUploadResponse;
import com.gym_membership.dto.response.MemberProfileResponse;
import com.gym_membership.dto.response.PaymentHistoryResponse;
import com.gym_membership.dto.response.PendingMembershipResponse;
import com.gym_membership.dto.response.TrainerMemberDetailsResponse;
import com.gym_membership.dto.response.TrainerMemberResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.mapper.MemberMapper;
import com.gym_membership.mapper.PaymentMapper;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.repositories.PaymentRepository;
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
    private final MemberMapper memberMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

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
                .gender(member.getGender() != null ? member.getGender() : null)
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
	
	@Override
	@Transactional(readOnly = true)
	public ApiResponse<?> getAllMembers() {

	    List<Member> members = memberRepo.findAllByOrderByCreatedAtDesc();

	    List<TrainerMemberResponse> response = members.stream()
	            .map(member -> {

	                TrainerMemberResponse dto = memberMapper.toTrainerResponse(member);

	                Membership membership = membershipRepo
	                        .findFirstByMemberAndStatusOrderByStartDateAsc(
	                                member,
	                                MembershipStatus.ACTIVE)
	                        .orElse(null);

	                if (membership == null) {
	                    membership = membershipRepo
	                            .findFirstByMemberAndStatusOrderByStartDateAsc(
	                                    member,
	                                    MembershipStatus.EXPIRED)
	                            .orElse(null);
	                }

	                if (membership == null) {
	                    return null;   // Skip this member
	                }

	                dto.setCurrentPlan(
	                        membership.getMembershipPlan().getPlanName());

	                dto.setMembershipStatus(
	                        membership.getStatus());

	                return dto;

	            })
	            .filter(Objects::nonNull)
	            .toList();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Members fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<?> getMemberDetails(Long memberId) {

	    // Fetch Member
	    Member member = memberRepo.findById(memberId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Member not found."));

	    // ===========================
	    // Member Profile
	    // ===========================
	    MemberProfileResponse memberProfile = MemberProfileResponse.builder()
	            .id(member.getId())
	            .fullName(member.getFullName())
	            .email(member.getUser().getEmail())
	            .phone(member.getPhone())
	            .gender(member.getGender() != null ? member.getGender() : null)
	            .age(member.getAge())
	            .height(member.getHeight())
	            .weight(member.getWeight())
	            .profileImage(member.getProfileImage())
	            .build();

	    // ===========================
	    // Current Membership
	    // ===========================
	    Membership activeMembership = membershipRepo
	            .findFirstByMemberAndStatusOrderByStartDateAsc(
	                    member,
	                    MembershipStatus.ACTIVE)
	            .orElse(null);

	    CurrentMembershipResponse currentMembership = null;

	    if (activeMembership != null) {

	        currentMembership = CurrentMembershipResponse.builder()
	                .planName(activeMembership.getMembershipPlan().getPlanName())
	                .status(activeMembership.getStatus())
	                .startDate(activeMembership.getStartDate())
	                .expiryDate(activeMembership.getExpiryDate())
	                .build();
	    }

	    // ===========================
	    // Pending Membership Queue
	    // ===========================
	    List<PendingMembershipResponse> pendingMemberships =
	            membershipRepo
	                    .findByMemberAndStatusOrderByCreatedAtAsc(
	                            member,
	                            MembershipStatus.PENDING)
	                    .stream()
	                    .map(membership -> PendingMembershipResponse.builder()
	                            .planName(membership.getMembershipPlan().getPlanName())
	                            .status(membership.getStatus())
	                            .createdAt(membership.getCreatedAt())
	                            .build())
	                    .toList();

	    // ===========================
	    // Payment History
	    // ===========================
//	    List<PaymentHistoryResponse> paymentHistory =
//	            paymentRepository
//	                    .findByMemberOrderByCreatedAtDesc(member)
//	                    .stream()
//	                    .map(payment -> {
//
//	                        Membership membership = membershipRepo
//	                                .findByPayment(payment)
//	                                .orElse(null);
//
//	                        return paymentMapper.toHistoryResponse(
//	                                payment,
//	                                membership);
//	                    })
//	                    .toList();
//
//	    // ===========================
//	    // Final Response
//	    // ===========================
	    TrainerMemberDetailsResponse response =
	            TrainerMemberDetailsResponse.builder()
	                    .member(memberProfile)
	                    .currentMembership(currentMembership)
	                    .pendingMemberships(pendingMemberships)
//	                    .paymentHistory(paymentHistory)
	                    .build();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Member details fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

}