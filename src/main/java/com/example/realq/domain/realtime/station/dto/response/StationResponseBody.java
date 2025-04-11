package com.example.realq.domain.realtime.station.dto.response;

public record StationResponseBody(
        StationBody body
) {
    public record StationBody(
            StationItem[] items
    ) {}
}
