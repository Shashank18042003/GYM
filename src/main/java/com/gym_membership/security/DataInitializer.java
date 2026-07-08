package com.gym_membership.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gym_membership.entity.Trainer;
import com.gym_membership.entity.User;
import com.gym_membership.enums.Role;
import com.gym_membership.repositories.TrainerRepo;
import com.gym_membership.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final TrainerRepo trainerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if(userRepo.existsByEmail("trainer@gym.com"))
            return;

        User trainerUser = User.builder()
                .username("trainer")
                .email("trainer@gym.com")
                .password(passwordEncoder.encode("Trainer@123"))
                .role(Role.ROLE_TRAINER)
                .enabled(true)
                .build();

        trainerUser = userRepo.save(trainerUser);

        Trainer trainer = Trainer.builder()
                .user(trainerUser)
                .fullName("Default Trainer")
                .phone("9876543211")
                .experience(5)
                .specialization("General Fitness")
                .build();

        trainerRepo.save(trainer);
    }
}
