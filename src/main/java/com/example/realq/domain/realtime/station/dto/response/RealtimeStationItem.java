package com.example.realq.domain.realtime.station.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RealtimeStationItem(
        String stationName,
        String dataTime,
        String pm10Value,
        String pm25Value,
        String o3Value,
        String no2Value,
        String coValue,
        String so2Value
) {}
