package com.gym_membership.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gym_membership.services.MembershipService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MembershipScheduler {
	private final MembershipService membershipService;
	
	@Scheduled(cron = "0 0 0 * * *")
    public void processMembershipQueue() {

        log.info("Running Membership Scheduler...");

        membershipService.processExpiredMemberships();

        log.info("Membership Scheduler Completed.");

    }

}
