package com.example.realq.domain.realtime.station.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StationItem(
        @JsonProperty("stationName") String stationName,
        @JsonProperty("dataTime") String dataTime,
        @JsonProperty("pm10Value") String pm10Value,
        @JsonProperty("pm25Value") String pm25Value,
        @JsonProperty("o3Value") String o3Value,
        @JsonProperty("no2Value") String no2Value,
        @JsonProperty("coValue") String coValue,
        @JsonProperty("so2Value") String so2Value
) {}
