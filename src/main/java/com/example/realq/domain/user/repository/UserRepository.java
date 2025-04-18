package com.example.realq.domain.user.repository;

import com.example.realq.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySlackId(String slackId);

    boolean existsBySlackId(String slackId);
}
