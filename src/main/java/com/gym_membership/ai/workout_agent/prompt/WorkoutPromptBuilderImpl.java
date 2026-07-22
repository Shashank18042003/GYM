package com.gym_membership.ai.workout_agent.prompt;

import org.springframework.stereotype.Component;

import com.gym_membership.ai.workout_agent.model.WorkoutRequest;
import com.gym_membership.entity.Member;

@Component
public class WorkoutPromptBuilderImpl implements WorkoutPromptBuilder {

    @Override
    public String buildPrompt(Member member, WorkoutRequest request) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are a certified professional fitness trainer.

                Generate a safe, personalized, and scientifically accurate workout plan.

                ============================
                MEMBER PROFILE
                ============================
                """);

        prompt.append("\nAge: ").append(member.getAge());
        prompt.append("\nGender: ").append(member.getGender());
        prompt.append("\nHeight: ").append(member.getHeight()).append(" cm");
        prompt.append("\nWeight: ").append(member.getWeight()).append(" kg");

        prompt.append("""

                ============================
                WORKOUT REQUIREMENTS
                ============================
                """);

        prompt.append("\nGoal: ").append(request.getGoal());
        prompt.append("\nExperience: ").append(request.getExperience());
        prompt.append("\nTraining Days Per Week: ").append(request.getTrainingDays());
        prompt.append("\nEquipment: ").append(request.getEquipment());
        prompt.append("\nTarget Body Parts: ").append(request.getBodyParts());

        prompt.append("""

                ============================
                INSTRUCTIONS
                ============================
                1. Create a day-wise workout schedule.
                2. Mention exercises for each day.
                3. Mention sets and repetitions.
                4. Mention rest time between sets.
                5. Include warm-up and cool-down.
                6. Keep exercises suitable for the user's experience level.
                7. Avoid unsafe exercises.
                8. Do not recommend steroids or harmful practices.
                9. Keep the response well formatted.
                """);

        return prompt.toString();
    }
}