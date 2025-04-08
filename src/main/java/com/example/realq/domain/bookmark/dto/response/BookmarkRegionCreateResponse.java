package com.example.realq.domain.bookmark.dto.response;

import com.example.realq.domain.bookmark.entity.BookmarkRegion;

public record BookmarkRegionCreateResponse(
        Long id,
        Long regionId,
        String regionName
) {

    public static BookmarkRegionCreateResponse toDto(BookmarkRegion bookmarkRegion) {
        return new BookmarkRegionCreateResponse(
                bookmarkRegion.getId(),
                bookmarkRegion.getRegion().getId(),
                bookmarkRegion.getRegion().getName()
        );
    }
}