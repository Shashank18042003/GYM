package com.gym_membership.servicesImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym_membership.dto.response.ApiResponse;
import com.gym_membership.dto.response.TrainerDashboardResponse;
import com.gym_membership.enums.MembershipStatus;
import com.gym_membership.enums.PaymentStatus;
import com.gym_membership.repositories.EventRepository;
import com.gym_membership.repositories.MembershipRepository;
import com.gym_membership.repositories.PaymentRepository;
import com.gym_membership.services.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
	
	
	private final MembershipRepository membershipRepository;

    private final PaymentRepository paymentRepository;

    private final EventRepository eventRepository;
    
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<?> getTrainerDashboard() {

        long activeMembers = membershipRepository
                .countByStatus(MembershipStatus.ACTIVE);

        long expiredMembers = membershipRepository
                .countByStatus(MembershipStatus.EXPIRED);

        long totalMembers = activeMembers + expiredMembers;

        long renewalDueMembers = membershipRepository
                .countByStatusAndExpiryDateBetween(
                        MembershipStatus.ACTIVE,
                        LocalDate.now(),
                        LocalDate.now().plusDays(3));

        BigDecimal todayRevenue = paymentRepository.getTodayRevenue();

        BigDecimal monthlyRevenue = paymentRepository.getMonthlyRevenue();

        long successfulPayments = paymentRepository
                .countByPaymentStatus(PaymentStatus.SUCCESS);

        long upcomingEvents = eventRepository
                .countByEventDateGreaterThanEqual(LocalDate.now());

        TrainerDashboardResponse response =
                TrainerDashboardResponse.builder()
                        .totalMembers(totalMembers)
                        .activeMembers(activeMembers)
                        .expiredMembers(expiredMembers)
                        .renewalDueMembers(renewalDueMembers)
                        .todayRevenue(todayRevenue)
                        .monthlyRevenue(monthlyRevenue)
                        .totalSuccessfulPayments(successfulPayments)
                        .totalUpcomingEvents(upcomingEvents)
                        .build();

        return ApiResponse.builder()
                .success(true)
                .message("Dashboard fetched successfully.")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
