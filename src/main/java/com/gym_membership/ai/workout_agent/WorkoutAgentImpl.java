package com.gym_membership.ai.workout_agent;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.common.service.AiChatService;
import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutAgentImpl implements WorkoutAgent {
	
	private final AiChatService aiChatService;

    @Override
    public ChatResponse process(ChatRequest request) {
    	// injecting the azure chat to workout agent
    	String answer = aiChatService.chat(request.getMessage());

        return ChatResponse.builder()
                .response(answer)
                .build();
    }
}
