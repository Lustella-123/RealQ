package com.example.realq.domain.realtime.station.dto.response;

public record RealtimeStationResponseBody(
        StationBody body
) {
    public record StationBody(
            RealtimeStationItem[] items
    ) {}
}
