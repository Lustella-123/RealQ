package com.example.realq.domain.average.region.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "average_region")
@NoArgsConstructor
public class AverageRegion {

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

    public static AverageRegion toEntity(
            String regionName,
            String pm10Value,
            String pm25Value
    ) {
        AverageRegion averageRegion = new AverageRegion();
        averageRegion.regionName = regionName;
        averageRegion.pm10Value = pm10Value;
        averageRegion.pm25Value = pm25Value;
        averageRegion.createdAt = LocalDateTime.now();
        return averageRegion;
    }
}
