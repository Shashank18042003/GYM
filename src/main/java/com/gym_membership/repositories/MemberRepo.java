package com.gym_membership.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_membership.entity.Member;
@Repository
public interface MemberRepo extends JpaRepository<Member, Long>{
	Optional<Member> findByPhone(String phone);
	boolean existsByPhone(String phone);
	Optional<Member> findByUserEmail(String email);

}
