package com.example.realq.domain.average.station;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "average_station")
@NoArgsConstructor
public class AverageStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    String pm10Value;

    @Column(nullable = false)
    String pm25Value;

    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdAt;

    public static AverageStation toEntity(
            String stationName,
            String pm10Value,
            String pm25Value
    ) {
        AverageStation averageStation = new AverageStation();
        averageStation.stationName = stationName;
        averageStation.pm10Value = pm10Value;
        averageStation.pm25Value = pm25Value;
        averageStation.createdAt = LocalDateTime.now();
        return averageStation;
    }
}
