package com.gym_membership.entity;

import java.time.LocalDate;

import com.gym_membership.enums.Gender;
import com.gym_membership.enums.MemberStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id",nullable = false,unique = true)
	private User user;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false,unique = true)
	private String phone;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private LocalDate dob;
	private Double height;
	private Double weight;
	private String address;
	private String profileImage;
	
	@Enumerated(EnumType.STRING)
	private MemberStatus status;

}
