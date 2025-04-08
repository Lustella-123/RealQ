package com.example.realq.domain.bookmark.repository;

import com.example.realq.domain.bookmark.entity.BookmarkStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkStationRepository extends JpaRepository<BookmarkStation, Long> {

    Optional<BookmarkStation> findByUserEmailAndStationId(String email, Long stationId);

    List<BookmarkStation> findByUserEmail(String email);
}
