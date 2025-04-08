package com.example.realq.domain.bookmark.controller;

import com.example.realq.domain.bookmark.dto.request.BookmarkStationCreateRequest;
import com.example.realq.domain.bookmark.dto.response.BookmarkStationCreateResponse;
import com.example.realq.domain.bookmark.dto.response.BookmarkStationReadResponse;
import com.example.realq.domain.bookmark.service.BookmarkStationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks/stations")
@RequiredArgsConstructor
public class BookmarkStationController {

    private final BookmarkStationService bookmarkStationService;

    @PostMapping
    public ResponseEntity<BookmarkStationCreateResponse> registerBookmarkStation(
            @RequestBody BookmarkStationCreateRequest requestDto,
            HttpSession session
    ) {
        BookmarkStationCreateResponse responseDto = bookmarkStationService.registerBookmarkStation(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkStationReadResponse>> readAllBookmarkStations(HttpSession session) {

        List<BookmarkStationReadResponse> responseDtoList = bookmarkStationService.readAllBookmarkStations(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookmarkStation(@RequestParam List<Long> bookmarkStationIdList) {

        bookmarkStationService.deleteBookmarkStation(bookmarkStationIdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}