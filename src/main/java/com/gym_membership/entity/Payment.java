package com.gym_membership.entity;

import java.math.BigDecimal;

import com.gym_membership.enums.PaymentMethod;
import com.gym_membership.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",nullable = false)
	private Member member;
	
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "membership_plan_id", nullable = false)
	private MembershipPlan membershipPlan;
	
	@Column(nullable=false)
	private String planName;
	
	@Column(nullable = false,precision = 10,scale = 2)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false )
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;
	
	@Column(nullable = false,unique = true)
	private String paymentReference;
	
	@Column(unique = true)
	private String razorpayOrderId;
	
	@Column(unique = true)
	private String razorpayPaymentId;
	
	@Column(name="signature")
	private String razorpaySignature;

}
