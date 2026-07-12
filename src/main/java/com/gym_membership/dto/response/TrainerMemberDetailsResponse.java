package com.gym_membership.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerMemberDetailsResponse {

    private MemberProfileResponse member;

    private CurrentMembershipResponse currentMembership;

    private List<PendingMembershipResponse> pendingMemberships;

//    private List<PaymentHistoryResponse> paymentHistory;

}
