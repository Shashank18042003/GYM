package com.gym_membership.ai.workout_agent.extractor;

import org.springframework.stereotype.Component;

import com.gym_membership.ai.common.service.AiChatService;
import com.gym_membership.ai.workout_agent.model.WorkoutRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WorkoutInformationExtractorImpl
        implements WorkoutInformationExtractor {

    private final WorkoutExtractionPromptBuilder promptBuilder;
    private final AiChatService aiChatService;

    @Override
    public WorkoutRequest extract(String message) {

        // Build the extraction prompt
        String prompt = promptBuilder.buildPrompt(message);

        // Ask AI to extract the information and map it directly to WorkoutRequest
        return aiChatService.chat(
                prompt,
                WorkoutRequest.class
        );
    }
}