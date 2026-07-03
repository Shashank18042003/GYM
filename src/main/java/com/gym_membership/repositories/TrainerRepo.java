package com.gym_membership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_membership.entity.Trainer;
@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Long>{

}
