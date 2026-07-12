package com.gym_membership.servicesImpl;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gym_membership.dto.response.CreateOrderResponse;
import com.gym_membership.entity.MembershipPlan;
import com.gym_membership.exceptions.PaymentException;
import com.gym_membership.services.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

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
	@Value("${razorpay.key.id}")
	private String keyId;
	
	@Value("${razorpay.currency}")
	private String currency;
	
	@Value("${razorpay.key.secret}")
	private String keySecret;
	
	private final RazorpayClient razorpayClient;
	
	@Override
	public CreateOrderResponse createOrder(MembershipPlan membershipPlan, String paymentReference) {
		try {

            JSONObject options = new JSONObject();

            // Razorpay expects amount in paise
            options.put("amount", membershipPlan.getPrice().multiply(BigDecimal.valueOf(100)).longValue());

            options.put("currency", currency);

            options.put("receipt", paymentReference);

            Order order = razorpayClient.orders.create(options);

            return CreateOrderResponse.builder()
                    .key(keyId)
                    .orderId(order.get("id").toString())
                    .amount(((Number) order.get("amount")).longValue())
                    .currency(order.get("currency").toString())
                    .build();

        } catch (RazorpayException e) {

            throw new PaymentException(
                    "Failed to create Razorpay order.");

        }
	}
	
	/**
	 * Find Payment
	 * 
	 * ↓
	 * 
	 * Already SUCCESS?
	 * 
	 * ↓
	 * 
	 * Reject
	 * 
	 * ↓
	 * 
	 * Verify Signature
	 * 
	 * ↓
	 * 
	 * SUCCESS
	 * 
	 * ↓
	 * 
	 * Update Payment
	 * 
	 * ↓
	 * 
	 * Create Membership
	 * 
	 * ↓
	 * 
	 * Return Success
	 */
	@Override
	public void verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
		 try {

		        JSONObject options = new JSONObject();

		        options.put("razorpay_order_id", razorpayOrderId);
		        options.put("razorpay_payment_id", razorpayPaymentId);
		        options.put("razorpay_signature", razorpaySignature);

		        Utils.verifyPaymentSignature(options, keySecret);

		    } catch (RazorpayException e) {

		        throw new PaymentException(
		                "Payment signature verification failed.");

		    }
	}
	
}
