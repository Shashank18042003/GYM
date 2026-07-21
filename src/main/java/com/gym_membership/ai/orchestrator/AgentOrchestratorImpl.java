package com.gym_membership.ai.orchestrator;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.common.enums.AgentType;
import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;
import com.gym_membership.ai.workout_agent.WorkoutAgent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentOrchestratorImpl implements AgentOrchestrator {
	
	private final WorkoutAgent workoutAgent;
	private final AgentRouter agentRouter;

    @Override
    public ChatResponse process(ChatRequest request) {

    	AgentType agentType = agentRouter.determineAgent(request.getMessage());

        switch (agentType) {

            case WORKOUT:
                return workoutAgent.process(request);

            case DIET:
                return ChatResponse.builder()
                        .response("Diet Agent is under development.")
                        .build();

            case GYM:
            default:
                return ChatResponse.builder()
                        .response("Gym Agent is under development.")
                        .build();
        }
    }
}