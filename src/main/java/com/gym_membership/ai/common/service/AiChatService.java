package com.gym_membership.ai.common.service;

public interface AiChatService {
	String chat(String prompt);
	<T> T chat(String prompt, Class<T> responseType);

}
