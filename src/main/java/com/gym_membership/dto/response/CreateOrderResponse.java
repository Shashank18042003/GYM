package com.gym_membership.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponse {

    private String orderId;

    private Long amount;

    private String currency;

    private String key;

}