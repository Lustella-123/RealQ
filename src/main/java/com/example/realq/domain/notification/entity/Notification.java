package com.example.realq.domain.notification.entity;

import com.example.realq.domain.station.entity.Station;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Station station;

    private Integer pm10Threshold;

    private Integer pm25Threshold;

    private Boolean enabled;
}