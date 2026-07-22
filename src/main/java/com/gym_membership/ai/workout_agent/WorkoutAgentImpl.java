package com.gym_membership.ai.workout_agent;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.common.service.AiChatService;
import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;
import com.gym_membership.ai.workout_agent.extractor.WorkoutInformationExtractor;
import com.gym_membership.ai.workout_agent.model.WorkoutRequest;
import com.gym_membership.ai.workout_agent.prompt.WorkoutPromptBuilder;
import com.gym_membership.entity.Member;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.repositories.MemberRepo;
import com.gym_membership.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutAgentImpl implements WorkoutAgent {

    private final AiChatService aiChatService;
    private final WorkoutPromptBuilder promptBuilder;
    private final WorkoutInformationExtractor workoutInformationExtractor;
    private final MemberRepo memberRepo;

    @Override
    public ChatResponse process(ChatRequest request) {

        WorkoutRequest extractedRequest =
                workoutInformationExtractor.extract(request.getMessage());

        String email = SecurityUtil.getLoggedInUserEmail();

        Member member = memberRepo
                .findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        String prompt = promptBuilder.buildPrompt(
                member,
                extractedRequest
        );

        String answer = aiChatService.chat(prompt);

        return ChatResponse.builder()
                .response(answer)
                .build();
    }
}