package com.example.realq.domain.bookmark.controller;

import com.example.realq.domain.bookmark.dto.request.BookmarkRegionCreateRequest;
import com.example.realq.domain.bookmark.dto.response.BookmarkRegionCreateResponse;
import com.example.realq.domain.bookmark.dto.response.BookmarkRegionReadResponse;
import com.example.realq.domain.bookmark.service.BookmarkRegionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks/regions")
@RequiredArgsConstructor
public class BookmarkRegionController {

    private final BookmarkRegionService bookmarkRegionService;

    @PostMapping
    public ResponseEntity<BookmarkRegionCreateResponse> registerBookmarkRegion(
            @RequestBody BookmarkRegionCreateRequest requestDto,
            HttpSession session
    ) {
        BookmarkRegionCreateResponse responseDto = bookmarkRegionService.registerBookmarkRegion(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkRegionReadResponse>> readAllBookmarkRegions(HttpSession session) {

        List<BookmarkRegionReadResponse> responseDtoList = bookmarkRegionService.readAllBookmarkRegions(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookmarkRegion(@RequestParam List<Long> bookmarkRegionIdList) {

        bookmarkRegionService.deleteBookmarkRegion(bookmarkRegionIdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}