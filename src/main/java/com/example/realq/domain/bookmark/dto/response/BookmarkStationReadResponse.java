package com.example.realq.domain.bookmark.dto.response;

public record BookmarkStationReadResponse(
        Long id,
        Long stationId,
        String stationName,
        Long regionId,
        String regionName
) {
    public static BookmarkStationReadResponse toDto(
            Long id,
            Long stationId,
            String stationName,
            Long regionId,
            String regionName
    ) {
        return new BookmarkStationReadResponse(
                id,
                stationId,
                stationName,
                regionId,
                regionName
        );
    }
}
