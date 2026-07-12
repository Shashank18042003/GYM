package com.gym_membership.servicesImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym_membership.dto.request.CreateEventRequest;
import com.gym_membership.dto.request.UpdateEventRequest;
import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.EventResponse;
import com.gym_membership.entity.Event;
import com.gym_membership.exceptions.EventException;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.mapper.EventMapper;
import com.gym_membership.repositories.EventRepository;
import com.gym_membership.services.EventService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
	
	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	
	
	@Override
	@Transactional
	public ApiResponse<?> createEvent(CreateEventRequest request) {

	    // Validate Event Date
	    if (request.getEventDate().isBefore(LocalDate.now())) {
	        throw new EventException("Event date cannot be in the past.");
	    }

	    // Check Duplicate Event
	    boolean exists = eventRepository
	            .existsByTitleAndEventDateAndEventTime(
	                    request.getTitle(),
	                    request.getEventDate(),
	                    request.getEventTime());

	    if (exists) {
	        throw new EventException("An event already exists for the selected date and time.");
	    }

	    // Create Event
	    Event event = eventMapper.toEntity(request);

	    event = eventRepository.save(event);

	    // Response
	    EventResponse response = eventMapper.toResponse(event);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Event created successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<?> getAllEvents() {

	    List<EventResponse> response = eventRepository
	            .findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(
	                    LocalDate.now())
	            .stream()
	            .map(eventMapper::toResponse)
	            .toList();

	    return ApiResponse.builder()
	            .success(true)
	            .message("Events fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<?> getEvent(Long eventId) {

	    // Fetch Event
	    Event event = eventRepository.findById(eventId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Event not found."));

	    // Response
	    EventResponse response = eventMapper.toResponse(event);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Event fetched successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	@Transactional
	public ApiResponse<?> updateEvent(
	        Long eventId,
	        UpdateEventRequest request) {

	    // Fetch Event
	    Event event = eventRepository.findById(eventId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Event not found."));

	    // Validate Event Date
	    if (request.getEventDate().isBefore(LocalDate.now())) {
	        throw new EventException("Event date cannot be in the past.");
	    }

	    // Duplicate Validation
	    boolean exists = eventRepository
	            .existsByTitleAndEventDateAndEventTime(
	                    request.getTitle(),
	                    request.getEventDate(),
	                    request.getEventTime());

	    // Ignore the current event
	    if (exists &&
	        !(event.getTitle().equals(request.getTitle())
	        && event.getEventDate().equals(request.getEventDate())
	        && event.getEventTime().equals(request.getEventTime()))) {

	        throw new EventException(
	                "An event already exists for the selected date and time.");
	    }

	    // Update Event
	    event.setTitle(request.getTitle());
	    event.setDescription(request.getDescription());
	    event.setEventDate(request.getEventDate());
	    event.setEventTime(request.getEventTime());
	    event.setLocation(request.getLocation());

	    event = eventRepository.save(event);

	    EventResponse response = eventMapper.toResponse(event);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Event updated successfully.")
	            .data(response)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

	@Override
	@Transactional
	public ApiResponse<?> deleteEvent(Long eventId) {

	    // Fetch Event
	    Event event = eventRepository.findById(eventId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Event not found."));

	    // Delete Event
	    eventRepository.delete(event);

	    return ApiResponse.builder()
	            .success(true)
	            .message("Event deleted successfully.")
	            .data(null)
	            .timestamp(LocalDateTime.now())
	            .build();
	}

}
