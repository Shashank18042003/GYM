package com.gym_membership.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.dto.request.UpdateProfileRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.services.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> getProfile() {
        return ResponseEntity.ok(memberService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(memberService.updateProfile(request));
    }
    
    @PostMapping("/profile-picture")
    public ResponseEntity<ApiResponse<?>> uploadProfilePicture(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                memberService.uploadProfilePicture(file));

    }
}