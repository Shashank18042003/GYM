package com.gym_membership.scheduler;


import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gym_membership.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void removeExpiredEvents() {

    	eventRepository.deleteByEventDateBefore(
                LocalDate.now());

            System.out.println(
                 
                     " expired events deleted.");
 

    }

}
