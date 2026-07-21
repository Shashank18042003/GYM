package com.gym_membership.ai.orchestrator;


import org.springframework.stereotype.Component;

import com.gym_membership.ai.common.enums.AgentType;

@Component
public class AgentRouter {

    public AgentType determineAgent(String message) {

        String input = message.toLowerCase();

        if (input.contains("workout")
                || input.contains("exercise")
                || input.contains("chest")
                || input.contains("legs")
                || input.contains("shoulder")
                || input.contains("back")) {

            return AgentType.WORKOUT;
        }

        if (input.contains("diet")
                || input.contains("meal")
                || input.contains("protein")
                || input.contains("nutrition")
                || input.contains("calories")) {

            return AgentType.DIET;
        }

        return AgentType.GYM;
    }

}
