package com.gym_membership.ai.workout_agent;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;

@Service
public class WorkoutAgentImpl implements WorkoutAgent {

    @Override
    public ChatResponse process(ChatRequest request) {

        return ChatResponse.builder()
                .response("Workout Agent initialized successfully.")
                .build();
    }
}
