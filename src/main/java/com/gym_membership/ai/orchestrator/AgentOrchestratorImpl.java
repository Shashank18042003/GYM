package com.gym_membership.ai.orchestrator;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;
import com.gym_membership.ai.workout_agent.WorkoutAgent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentOrchestratorImpl implements AgentOrchestrator {
	
	private final WorkoutAgent workoutAgent;

    @Override
    public ChatResponse process(ChatRequest request) {

        return workoutAgent.process(request);
    }
}