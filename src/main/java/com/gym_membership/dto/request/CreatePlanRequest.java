package com.gym_membership.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePlanRequest {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationInDays;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than zero")
    private BigDecimal price;

    private String description;

}