package com.gym_membership.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateEventRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Event date is required.")
    private LocalDate eventDate;

    @NotNull(message = "Event time is required.")
    private LocalTime eventTime;

    @NotBlank(message = "Location is required.")
    private String location;

}
