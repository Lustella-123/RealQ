package com.example.realq.domain.notification.station.repository;

import com.example.realq.domain.notification.station.entity.NotificationStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationStationRepository extends JpaRepository<NotificationStation, Long> {

    Optional<NotificationStation> findByUserEmailAndStationId(String email, Long stationId);

    List<NotificationStation> findByUserEmail(String email);

}
