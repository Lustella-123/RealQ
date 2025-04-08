package com.example.realq.domain.bookmark.dto.response;

import com.example.realq.domain.bookmark.entity.BookmarkStation;

public record BookmarkStationReadResponse(
        Long id,
        Long stationId,
        String stationName,
        Long regionId,
        String regionName
) {
    public static BookmarkStationReadResponse toDto(BookmarkStation bookmarkStation) {
        return new BookmarkStationReadResponse(
                bookmarkStation.getId(),
                bookmarkStation.getStation().getId(),
                bookmarkStation.getStation().getName(),
                bookmarkStation.getRegion().getId(),
                bookmarkStation.getRegion().getName()
        );
    }
}
