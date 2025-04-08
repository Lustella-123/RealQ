package com.example.realq.domain.bookmark.dto.response;

import com.example.realq.domain.bookmark.entity.BookmarkStation;

public record BookmarkStationCreateResponse(
        Long id,
        Long stationId,
        String stationName,
        Long regionId,
        String regionName
) {
    public static BookmarkStationCreateResponse toDto(BookmarkStation bookmarkStation) {
        return new BookmarkStationCreateResponse(
                bookmarkStation.getId(),
                bookmarkStation.getStation().getId(),
                bookmarkStation.getStation().getName(),
                bookmarkStation.getRegion().getId(),
                bookmarkStation.getRegion().getName()
        );
    }
}
