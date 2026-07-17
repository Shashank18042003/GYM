package com.gym_membership.ai.serviceImpl;


import org.springframework.stereotype.Service;

import com.gym_membership.ai.service.AIService;

@Service
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String chat(String message) {

        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}