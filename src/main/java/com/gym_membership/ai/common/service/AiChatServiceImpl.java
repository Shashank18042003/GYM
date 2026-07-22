package com.gym_membership.ai.common.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym_membership.exceptions.AiResponseParsingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Override
    public String chat(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
    
    @Override
    public <T> T chat(String prompt, Class<T> responseType) {

        String response = chat(prompt);

        try {

            return objectMapper.readValue(response, responseType);

        } catch (Exception e) {

        	throw new AiResponseParsingException(
        	        "Failed to parse AI response into "
        	                + responseType.getSimpleName(),
        	        e);

        }

    }
    
    
}
