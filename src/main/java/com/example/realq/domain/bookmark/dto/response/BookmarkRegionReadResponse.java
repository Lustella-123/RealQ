package com.example.realq.domain.bookmark.dto.response;

import com.example.realq.domain.bookmark.entity.BookmarkRegion;

public record BookmarkRegionReadResponse(
        Long id,
        Long regionId,
        String regionName
) {
    public static BookmarkRegionReadResponse toDto(BookmarkRegion bookmarkRegion) {
        return new BookmarkRegionReadResponse(
                bookmarkRegion.getId(),
                bookmarkRegion.getRegion().getId(),
                bookmarkRegion.getRegion().getName()
        );
    }
}
