package com.example.realq.domain.bookmark.dto.response;

public record BookmarkStationCreateResponse(
        Long id,
        Long stationId,
        String stationName,
        Long regionId,
        String regionName
) {
    public static BookmarkStationCreateResponse toDto(
            Long id,
            Long stationId,
            String stationName,
            Long regionId,
            String regionName
    ) {
        return new BookmarkStationCreateResponse(
                id,
                stationId,
                stationName,
                regionId,
                regionName
        );
    }
}
