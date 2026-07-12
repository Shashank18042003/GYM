package com.gym_membership.services;

import com.gym_membership.dto.request.CreateEventRequest;
import com.gym_membership.dto.request.UpdateEventRequest;
import com.gym_membership.dto.response.ApiResponse;

public interface EventService {

    ApiResponse<?> createEvent(CreateEventRequest request);

    ApiResponse<?> getAllEvents();

    ApiResponse<?> getEvent(Long eventId);

    ApiResponse<?> updateEvent(
            Long eventId,
            UpdateEventRequest request);

    ApiResponse<?> deleteEvent(Long eventId);

}
