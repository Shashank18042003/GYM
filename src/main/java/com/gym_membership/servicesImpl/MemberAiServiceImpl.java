package com.gym_membership.servicesImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;
import com.gym_membership.ai.orchestrator.AgentOrchestrator;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.services.MemberAiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAiServiceImpl implements MemberAiService {

	private final AgentOrchestrator agentOrchestrator;
    @Override
    public ApiResponse<?> chat(ChatRequest request) {

    	ChatResponse response = agentOrchestrator.process(request);

        return ApiResponse.<ChatResponse>builder()
                .success(true)
                .message("Response generated successfully.")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }
}