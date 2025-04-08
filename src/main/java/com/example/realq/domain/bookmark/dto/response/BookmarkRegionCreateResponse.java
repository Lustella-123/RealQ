package com.example.realq.domain.bookmark.dto.response;

public record BookmarkRegionCreateResponse (
        Long id,
        Long regionId,
        String regionName
) {

    public static BookmarkRegionCreateResponse toDto(
            Long id,
            Long regionId,
            String regionName
    ) {
        return new BookmarkRegionCreateResponse(id, regionId, regionName);
    }
}