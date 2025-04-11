package com.example.realq.domain.notificationpm25.entity;

import com.example.realq.domain.realtime.station.entity.Station;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notification_pm_2.5")
public class NotificationPm25 {

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
    private Integer pm25Threshold;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static NotificationPm25 toEntity(
            User user,
            Station station,
            Integer pm25Threshold
    ) {
        NotificationPm25 notificationPm25 = new NotificationPm25();
        notificationPm25.user = user;
        notificationPm25.station = station;
        notificationPm25.pm25Threshold = pm25Threshold;
        notificationPm25.createdAt = LocalDateTime.now();
        notificationPm25.updatedAt = LocalDateTime.now();
        return notificationPm25;
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
