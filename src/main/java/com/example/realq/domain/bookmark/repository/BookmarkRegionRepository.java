package com.example.realq.domain.bookmark.repository;

import com.example.realq.domain.bookmark.entity.BookmarkRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRegionRepository extends JpaRepository<BookmarkRegion, Long> {

    Optional<BookmarkRegion> findByUserSlackIdAndRegionId(String slackId, Long regionId);

    List<BookmarkRegion> findByUserSlackId(String slackId);
}
