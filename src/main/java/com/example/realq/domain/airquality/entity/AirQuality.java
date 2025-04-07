//package com.example.realq.domain.airquality.entity;
//
//import com.example.realq.domain.station.entity.Station;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Table(name = "air_quality")
//@NoArgsConstructor
//public class AirQuality {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "station_id")
//    private Station station;
//
//    private LocalDateTime dataTime;
//
//    private Integer pm10;      // 미세먼지 농도
//    private Integer pm25;      // 초미세먼지 농도
//    private String pm10Grade;  // 등급 1~4
//    private String pm25Grade;
//
//    private String khaiGrade;  // 통합대기환경지수
//
//    private Double so2;   // 아황산가스
//    private Double co;    // 일산화탄소
//    private Double o3;    // 오존
//    private Double no2;   // 이산화질소
//
//    public static AirQuality toEntity(
//            LocalDateTime dataTime,
//            int pm10,
//            int pm25
//    ) {
//        AirQuality airQuality = new AirQuality();
//        airQuality.dataTime = dataTime;
//        airQuality.pm10 = pm10;
//        airQuality.pm25 = pm25;
//
//        return airQuality;
//    }
//}