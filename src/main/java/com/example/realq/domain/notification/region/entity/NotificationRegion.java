package com.example.realq.domain.notification.region.entity;

import com.example.realq.domain.realtime.region.entity.Region;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notification_region")
public class NotificationRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Region region;

    @Column(nullable = false)
    private Integer pm10Threshold;

    @Column(nullable = false)
    private Integer pm25Threshold;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static NotificationRegion toEntity(
            User user,
            Region region,
            Integer pm10Threshold,
            Integer pm25Threshold
    ) {
        NotificationRegion notificationRegion = new NotificationRegion();
        notificationRegion.user = user;
        notificationRegion.region = region;
        notificationRegion.pm10Threshold = pm10Threshold;
        notificationRegion.pm25Threshold = pm25Threshold;
        notificationRegion.createdAt = LocalDateTime.now();
        notificationRegion.updatedAt = LocalDateTime.now();
        return notificationRegion;
    }

    public void updatePm10Threshold(Integer newPm10Threshold) {
        this.pm10Threshold = newPm10Threshold;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePm25Threshold(Integer newPm25Threshold) {
        this.pm25Threshold = newPm25Threshold;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEnabledStatus(Boolean newEnabled) {
        this.enabled = newEnabled;
        this.updatedAt = LocalDateTime.now();
    }
}
