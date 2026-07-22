package com.gym_membership.ai.workout_agent.prompt;

import com.gym_membership.ai.workout_agent.model.WorkoutRequest;
import com.gym_membership.entity.Member;

public interface WorkoutPromptBuilder {
	
	String buildPrompt(Member member, WorkoutRequest request);

}
