package com.example.realq.domain.realtime.region.dto.response;

public record RegionItem(
        String dataTime,
        String pm10Value,
        String pm25Value,
        String o3Value,
        String no2Value,
        String coValue,
        String so2Value
) {}
