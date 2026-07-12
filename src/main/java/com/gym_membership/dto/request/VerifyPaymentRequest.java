package com.gym_membership.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyPaymentRequest {
	@NotBlank(message = "Razorpay order_id is required")
	private String razorpayOrderId;
	@NotBlank(message = "Razorpay payment_id is required")
	private String razorpayPaymentId;
	@NotBlank(message = "Razorpay signature is required")
	private String razorpaySignature;
}
