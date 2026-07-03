package com.gym_membership.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
	@Column(name="created_at", nullable=false,updatable = false)
	private LocalDateTime createdAt;
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void onCreate() {
		createdAt =LocalDateTime.now();
		updatedAt= LocalDateTime.now();
	}
	@PreUpdate
	public void onUpdate() {
		updatedAt=LocalDateTime.now();
	}
}
