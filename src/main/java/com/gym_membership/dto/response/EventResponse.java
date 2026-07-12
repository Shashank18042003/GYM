package com.gym_membership.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {

    private Long eventId;

    private String title;

    private String description;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private String location;
    
    private Long daysRemaining;

}
