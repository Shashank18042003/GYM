package com.gym_membership.ai.workout_agent;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.common.service.AiChatService;
import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;
import com.gym_membership.ai.workout_agent.prompt.WorkoutPromptBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutAgentImpl implements WorkoutAgent {
	
	private final AiChatService aiChatService;
	private final WorkoutPromptBuilder workoutPromptBuilder;

    @Override
    public ChatResponse process(ChatRequest request) {
    	//injcecting prompt to workout agent
    	String prompt = workoutPromptBuilder.buildPrompt(request.getMessage());
    	// injecting the azure chat to workout agent
    	String answer = aiChatService.chat(prompt);

        return ChatResponse.builder()
                .response(answer)
                .build();
    }
}
