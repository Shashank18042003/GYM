package com.gym_membership.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePlanStatusRequest {
	
	@NotNull
	private Boolean active;
}
