package com.gym_membership.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.gym_membership.dto.request.CreateEventRequest;
import com.gym_membership.dto.response.EventResponse;
import com.gym_membership.entity.Event;

@Component
public class EventMapper {

	    public Event toEntity(CreateEventRequest request) {

	        Event event = new Event();

	        event.setTitle(request.getTitle());
	        event.setDescription(request.getDescription());
	        event.setEventDate(request.getEventDate());
	        event.setEventTime(request.getEventTime());
	        event.setLocation(request.getLocation());

	        return event;
	    }

	    
	    public EventResponse toResponse(Event event) {

	        long daysRemaining = ChronoUnit.DAYS.between(
	                LocalDate.now(),
	                event.getEventDate());

	        return EventResponse.builder()
	                .eventId(event.getId())
	                .title(event.getTitle())
	                .description(event.getDescription())
	                .eventDate(event.getEventDate())
	                .eventTime(event.getEventTime())
	                .location(event.getLocation())
	                .daysRemaining(daysRemaining)
	                .build();
	    }


}
