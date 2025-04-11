package com.example.realq.domain.avg.district.dto.response;

public record AvgDistrictItem(
        String stationName,
        String dataTime,
        String pm10Value,
        String pm25Value,
        String o3Value,
        String no2Value,
        String coValue,
        String so2Value
) {}
