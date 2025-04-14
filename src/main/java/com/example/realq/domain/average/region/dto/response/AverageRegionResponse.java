package com.example.realq.domain.average.region.dto.response;

public record AverageRegionResponse(
        String region,
        String dataTime,
        double pm10Value,
        double pm25Value,
        double o3Value,
        double no2Value,
        double coValue,
        double so2Value
) {
    public static AverageRegionResponse toDto(String region, String dataTime, double pm10, double pm25, double o3, double no2, double co2, double so2) {
        return new AverageRegionResponse(region, dataTime, pm10, pm25, o3, no2, co2, so2);
    }
}
