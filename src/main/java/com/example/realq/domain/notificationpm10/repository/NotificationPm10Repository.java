package com.example.realq.domain.notificationpm10.repository;

import com.example.realq.domain.notificationpm10.entity.NotificationPm10;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationPm10Repository extends JpaRepository<NotificationPm10, Long> {

    Optional<NotificationPm10> findByUserEmailAndStationId(String email, Long stationId);

    List<NotificationPm10> findByUserEmail(String email);

}
