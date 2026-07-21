package com.gym_membership.services;

import com.gym_membership.ai.dto.ChatRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface MemberAiService {
	
	ApiResponse<?> chat(ChatRequest request);

}
