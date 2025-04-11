package com.example.realq.domain.average.district.dto.response;

public record AverageDistrictResponse(
        String region,
        String dataTime,
        double pm10Value,
        double pm25Value,
        double o3Value,
        double no2Value,
        double coValue,
        double so2Value
) {
    public static AverageDistrictResponse toDto(String region, String dataTime, double pm10, double pm25, double o3, double no2, double co2, double so2) {
        return new AverageDistrictResponse(region, dataTime, pm10, pm25, o3, no2, co2, so2);
    }
}
