package com.gym_membership.ai.workout_agent.extractor;

import org.springframework.stereotype.Component;

@Component
public class WorkoutExtractionPromptBuilderImpl
        implements WorkoutExtractionPromptBuilder {

    @Override
    public String buildPrompt(String userMessage) {

        return """
                You are an expert fitness information extractor.

                Your job is to extract workout-related information from the user's message.

                Return ONLY valid JSON.

                JSON Schema:

                {
                  "goal": null,
                  "experience": null,
                  "trainingDays": null,
                  "bodyParts": [],
                  "equipment": null
                }

                Allowed goal values:
                - MUSCLE_GAIN
                - WEIGHT_LOSS
                - STRENGTH
                - ENDURANCE

                Allowed experience values:
                - BEGINNER
                - INTERMEDIATE
                - ADVANCED

                Allowed bodyParts values:
                - CHEST
                - BACK
                - LEGS
                - SHOULDERS
                - BICEPS
                - TRICEPS
                - ABS
                - FULL_BODY

                Allowed equipment values:
                - GYM
                - HOME
                - DUMBBELLS
                - BARBELL
                - MACHINES
                - BODY_WEIGHT

                If a value is not mentioned, return null.

                Return ONLY JSON.

                User Message:

                %s
                """.formatted(userMessage);

    }
}
