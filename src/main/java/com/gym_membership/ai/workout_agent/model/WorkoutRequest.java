package com.gym_membership.ai.workout_agent.model;


import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutRequest {

    private String goal;

    private String experience;

    private Integer trainingDays;

    private List<String> bodyParts;

    private String equipment;
    private Integer workoutDuration;

}