package com.example.realq.domain.bookmark.dto.response;

public record BookmarkRegionReadResponse (
        Long id,
        Long regionId,
        String regionName
) {
    public static BookmarkRegionReadResponse toDto(
            Long id,
            Long regionId,
            String regionName
    ) {
     return new BookmarkRegionReadResponse(id, regionId, regionName);
    }
}
