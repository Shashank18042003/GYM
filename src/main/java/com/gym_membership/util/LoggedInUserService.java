package com.gym_membership.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gym_membership.entity.Member;
import com.gym_membership.exceptions.ResourceNotFoundException;
import com.gym_membership.repositories.MemberRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoggedInUserService {

    private final MemberRepo memberRepository;

    public Member getLoggedInMember(Authentication authentication) {

        return memberRepository.findByUserEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found."));
    }

}
