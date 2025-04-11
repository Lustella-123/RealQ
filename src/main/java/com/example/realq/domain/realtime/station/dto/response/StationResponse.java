package com.example.realq.domain.realtime.station.dto.response;

public record StationResponse(
        String stationName,
        String dataTime,
        double pm10,
        double pm25,
        double o3,
        double no2,
        double co,
        double so2
) {}
