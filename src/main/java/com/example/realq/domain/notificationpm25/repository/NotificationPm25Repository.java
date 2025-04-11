package com.example.realq.domain.notificationpm25.repository;

import com.example.realq.domain.notificationpm25.entity.NotificationPm25;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationPm25Repository extends JpaRepository<NotificationPm25, Long> {
    Optional<NotificationPm25> findByUserEmailAndStationId(String email, Long stationId);

    List<NotificationPm25> findByUserEmail(String email);
}
