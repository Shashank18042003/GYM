package com.gym_membership.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipPlanResponse {
	
	private Long id;
	private String planName;
	private Integer durationInDays;
	private BigDecimal price;
	private String description;
	private Boolean active;
//	private Boolean popular;
	

}
