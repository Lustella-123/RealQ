package com.example.realq.domain.slack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "average_district")
@NoArgsConstructor
public class AverageDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String regionName;

    @Column(nullable = false)
    String pm10Value;

    @Column(nullable = false)
    String pm25Value;

    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdAt;

    public static AverageDistrict toEntity(
            String regionName,
            String pm10Value,
            String pm25Value
    ) {
        AverageDistrict averageDistrict = new AverageDistrict();
        averageDistrict.regionName = regionName;
        averageDistrict.pm10Value = pm10Value;
        averageDistrict.pm25Value = pm25Value;
        averageDistrict.createdAt = LocalDateTime.now();
        return averageDistrict;
    }
}
