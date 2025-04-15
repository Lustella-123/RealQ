package com.example.realq.domain.notification.region.repository;

import com.example.realq.domain.notification.region.entity.NotificationRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRegionRepository extends JpaRepository<NotificationRegion, Long> {

    Optional<NotificationRegion> findByUserSlackIdAndRegionId(String slackId, Long regionId);

    List<NotificationRegion> findByUserSlackId(String slackId);

    List<NotificationRegion> findAllByEnabledTrue();
}
