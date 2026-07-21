package com.gym_membership.ai.orchestrator;



import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.ai.dto.ChatResponse;

public interface AgentOrchestrator {

    ChatResponse process(ChatRequest request);

}