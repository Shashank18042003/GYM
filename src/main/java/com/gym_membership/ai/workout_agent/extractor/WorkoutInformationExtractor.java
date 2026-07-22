package com.gym_membership.ai.workout_agent.extractor;

import com.gym_membership.ai.workout_agent.model.WorkoutRequest;

public interface WorkoutInformationExtractor {
	
	 public WorkoutRequest extract(String message);

}
