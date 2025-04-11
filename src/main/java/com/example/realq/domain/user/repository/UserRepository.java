package com.example.realq.domain.user.repository;

import com.example.realq.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u.slackId FROM User u WHERE u.slackId IS NOT NULL")
    List<String> findAllSlackIdList();
}
