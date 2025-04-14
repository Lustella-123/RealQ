package com.example.realq.domain.average.region.dto.response;

public record AverageRegionItem(
        String stationName,
        String dataTime,
        String pm10Value,
        String pm25Value,
        String o3Value,
        String no2Value,
        String coValue,
        String so2Value
) {}
