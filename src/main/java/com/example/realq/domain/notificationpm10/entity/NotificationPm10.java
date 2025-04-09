package com.example.realq.domain.notificationpm10.entity;

import com.example.realq.domain.station.entity.Station;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notification_pm_10")
public class NotificationPm10 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Station station;

    @Column(nullable = false)
    private Integer pm10Threshold;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static NotificationPm10 toEntity(
            User user,
            Station station,
            Integer pm10Threshold
    ) {
        NotificationPm10 notificationPm10 = new NotificationPm10();
        notificationPm10.user = user;
        notificationPm10.station = station;
        notificationPm10.pm10Threshold = pm10Threshold;
        notificationPm10.createdAt = LocalDateTime.now();
        notificationPm10.updatedAt = LocalDateTime.now();
        return notificationPm10;
    }

    public void updatePm10Threshold(Integer newPm10Threshold) {
        this.pm10Threshold = newPm10Threshold;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEnabledStatus(Boolean newEnabled) {
        this.enabled = newEnabled;
        this.updatedAt = LocalDateTime.now();
    }
}