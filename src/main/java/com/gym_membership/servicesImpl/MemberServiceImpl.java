package com.gym_membership.servicesImpl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.CurrentMembershipResponse;
import com.gym_membership.dto.response.ImageUploadResponse;
import com.gym_membership.dto.response.MemberProfileResponse;
import com.gym_membership.dto.response.PendingMembershipResponse;
import com.gym_membership.dto.response.TrainerMemberDetailsResponse;
import com.gym_membership.dto.response.TrainerMemberResponse;
import com.gym_membership.entity.Member;
import com.gym_membership.entity.Membership;
import com.gym_membership.entity.User;
import com.gym_membership.enums.MemberFilter;
import com.gym_membership.enums.MemberStatus;
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
//    private final MemberMapper memberMapper;
//    private final PaymentRepository paymentRepository;
//    private final PaymentMapper paymentMapper;
//    private final UserRepo userRepo;

    @Override
    public ApiResponse<?> getProfile() {

        String email = SecurityUtil.getLoggedInUserEmail();

        Member member = memberRepo.findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        MemberProfileResponse response = MemberProfileResponse.builder()
                .id(member.getId())
                .username(member.getUser().getUsername())
                .age(member.getAge())
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
        member.getPhone();
        member.setAge(request.getAge());
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
	public ApiResponse<?> getMembers(MemberFilter filter, Integer days) {
		List<TrainerMemberResponse> response;

		if (filter == MemberFilter.ALL) {

		    response = memberRepo
		            .findByStatusOrderByCreatedAtDesc(MemberStatus.ACTIVE)
		            .stream()
		            .map(this::buildTrainerMemberResponse)
		            .toList();

		} else {

		    List<Membership> memberships = switch (filter) {

		        case ACTIVE ->
		                membershipRepo.findByStatusOrderByExpiryDateAsc(
		                        MembershipStatus.ACTIVE);

		        case EXPIRED ->
		                membershipRepo.findByStatusOrderByExpiryDateAsc(
		                        MembershipStatus.EXPIRED);

		        case RENEWAL_DUE -> {

		            int renewalDays = (days == null) ? 3 : days;

		            yield membershipRepo
		                    .findByStatusAndExpiryDateBetweenOrderByExpiryDateAsc(
		                            MembershipStatus.ACTIVE,
		                            LocalDate.now(),
		                            LocalDate.now().plusDays(renewalDays));
		        }

		        default -> new ArrayList<>();
		    };

		    response = memberships.stream()
		            .map(this::buildTrainerMemberResponse)
		            .toList();
		}

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
	            .status(member.getStatus())
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
	
	
	
	private TrainerMemberResponse buildTrainerMemberResponse(Membership membership) {

	    Member member = membership.getMember();

	    User user = member.getUser();

	    long daysLeft = ChronoUnit.DAYS.between(
	            LocalDate.now(),
	            membership.getExpiryDate());

	    return TrainerMemberResponse.builder()
	            .memberId(member.getId())
	            .fullName(member.getFullName())
	            .email(user.getEmail())
	            .phoneNumber(member.getPhone())
	            .currentPlan(membership.getMembershipPlan().getPlanName())
	            .membershipStatus(membership.getStatus())
	            .expiryDate(membership.getExpiryDate())
	            .daysLeft(daysLeft)
	            .build();
	}
	
	private TrainerMemberResponse buildTrainerMemberResponse(Member member) {

	    User user = member.getUser();

	    Membership membership = membershipRepo
	            .findFirstByMemberOrderByExpiryDateDesc(member)
	            .orElse(null);

	    TrainerMemberResponse.TrainerMemberResponseBuilder builder =
	            TrainerMemberResponse.builder()
	                    .memberId(member.getId())
	                    .fullName(member.getFullName())
	                    .email(user.getEmail())
	                    .phoneNumber(member.getPhone());

	    if (membership != null) {

	        Long daysLeft = null;

	        if (membership.getExpiryDate() != null) {
	            daysLeft = ChronoUnit.DAYS.between(
	                    LocalDate.now(),
	                    membership.getExpiryDate());
	        }

	        builder.currentPlan(membership.getMembershipPlan().getPlanName())
	                .membershipStatus(membership.getStatus())
	                .expiryDate(membership.getExpiryDate())
	                .daysLeft(daysLeft);
	    }

	    return builder.build();
	}
}