package com.example.realq.domain.realtime.region.dto.response;

public record RealtimeRegionResponse(
        String region,
        String dataTime,
        double pm10Avg,
        double pm25Avg,
        double o3Avg,
        double no2Avg,
        double coAvg,
        double so2Avg
) {
    public static RealtimeRegionResponse toDto(String region, String dataTime, double pm10, double pm25, double o3, double no2, double co2, double so2) {
        return new RealtimeRegionResponse(region, dataTime, pm10, pm25, o3, no2, co2, so2);
    }
}
