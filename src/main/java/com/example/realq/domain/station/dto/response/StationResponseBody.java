package com.example.realq.domain.station.dto.response;

public record StationResponseBody(
        StationBody body
) {
    public record StationBody(
            StationItem[] items
    ) {}
}
