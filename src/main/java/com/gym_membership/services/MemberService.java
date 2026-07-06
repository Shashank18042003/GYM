package com.gym_membership.services;

import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface MemberService {

    ApiResponse<?> getProfile();

    ApiResponse<?> updateProfile(UpdateProfileRequest request);
    ApiResponse<?> uploadProfilePicture(MultipartFile file);

}