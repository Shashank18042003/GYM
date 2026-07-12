package com.gym_membership.repositories;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gym_membership.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Trainer & Member
    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(
            LocalDate date);

    // Scheduler
    List<Event> findByEventDateBefore(LocalDate date);
    
    boolean existsByTitleAndEventDateAndEventTime(
            String title,
            LocalDate eventDate,
            LocalTime eventTime);
    @Transactional
    void deleteByEventDateBefore(LocalDate date);

}
