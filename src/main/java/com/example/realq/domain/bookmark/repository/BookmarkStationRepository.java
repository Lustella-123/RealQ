package com.example.realq.domain.bookmark.repository;

import com.example.realq.domain.bookmark.entity.BookmarkStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkStationRepository extends JpaRepository<BookmarkStation, Long> {

    Optional<BookmarkStation> findByUserSlackIdAndStationId(String slackId, Long stationId);

    List<BookmarkStation> findByUserSlackId(String slackId);
}