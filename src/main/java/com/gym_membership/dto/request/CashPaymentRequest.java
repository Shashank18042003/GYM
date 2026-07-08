package com.gym_membership.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CashPaymentRequest {
	
	@NotNull(message = "Plan Id is required")
	private Long planId;

	

}
