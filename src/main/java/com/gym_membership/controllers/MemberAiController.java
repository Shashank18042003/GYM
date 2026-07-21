package com.gym_membership.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.services.MemberAiService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member/ai")
@RequiredArgsConstructor
public class MemberAiController {

    private final MemberAiService memberAiService;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<?>> chat(
            @Valid @RequestBody ChatRequest request) {

        return ResponseEntity.ok(memberAiService.chat(request));
    }
}