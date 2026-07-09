package com.gym_membership.servicesImpl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gym_membership.dto.response.CreateOrderResponse;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.exceptions.PaymentException;
import com.gym_membership.services.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService{
	
	/**
	 * 		Member
	 *   	  ▼
	 * Select Membership Plan
	 *        ▼
	 * POST /member/payments/create-order
	 * 		  ▼
	 *  Validate Member & Plan
	 * 		  ▼	
	 * Create Razorpay Order (Gateway)
	 * 		  ▼
	 * Save Payment (Status = PENDING)
	 * 		  ▼
	 * Return Order Details to Frontend
	 * 		  ▼
	 *   Razorpay Checkout Opens
	 * ┌──────────┴──────────┐ 
	 * ▼                     ▼ 
	 * Payment Success      Payment Failed
	 * 	▼							     ▼
	 * POST /member/payments/verify     Update Payment FAILED
	 *			▼ 
	 *  Verify Razorpay Signature
	 *       ┌───┴────┐ 
	 *       ▼        ▼ 
	 *     Invalid    Valid
	 *      FAILED    SUCCESS
	 *                   ▼ 
	 *           	Update Payment Table
	 					 ▼ 
	 			membershipService.createMembership()
	 * 				     ▼
	 * 			ACTIVE or PENDING (Queue Logic)
	 */
	
	private String keyId;
	private String currency;
	private final RazorpayClient razorpayClient;
	@Override
	public CreateOrderResponse createOrder(MembershipPlan membershipPlan, String paymentReference) {
		try {

            JSONObject options = new JSONObject();

            // Razorpay expects amount in paise
            options.put("amount", membershipPlan.getPrice());

            options.put("currency", currency);

            options.put("receipt", paymentReference);

            Order order = razorpayClient.orders.create(options);

            return CreateOrderResponse.builder()
                    .key(keyId)
                    .orderId(order.get("id"))
                    .amount(order.get("amount"))
                    .currency(order.get("currency"))
                    .build();

        } catch (RazorpayException e) {

            throw new PaymentException(
                    "Failed to create Razorpay order.");

        }
	}
	@Override
	public boolean verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
