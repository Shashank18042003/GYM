package com.gym_membership.ai.workout_agent;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;

public interface WorkoutAgent {
	ChatResponse process(ChatRequest request);
}
