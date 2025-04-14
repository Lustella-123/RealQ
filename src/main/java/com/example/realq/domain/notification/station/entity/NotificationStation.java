package com.example.realq.domain.notification.station.entity;

import com.example.realq.domain.realtime.station.entity.Station;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notification_station")
@NoArgsConstructor
public class NotificationStation {

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
    private Integer pm25Threshold;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static NotificationStation toEntity(
            User user,
            Station station,
            Integer pm10Threshold,
            Integer pm25Threshold
    ) {
        NotificationStation notificationStation = new NotificationStation();
        notificationStation.user = user;
        notificationStation.station = station;
        notificationStation.pm10Threshold = pm10Threshold;
        notificationStation.pm25Threshold = pm25Threshold;
        notificationStation.createdAt = LocalDateTime.now();
        notificationStation.updatedAt = LocalDateTime.now();
        return notificationStation;
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