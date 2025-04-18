package com.example.realq.domain.bookmark.service;

import com.example.realq.domain.bookmark.dto.request.BookmarkRegionCreateRequest;
import com.example.realq.domain.bookmark.dto.response.BookmarkRegionCreateResponse;
import com.example.realq.domain.bookmark.dto.response.BookmarkRegionReadResponse;
import com.example.realq.domain.bookmark.entity.BookmarkRegion;
import com.example.realq.domain.bookmark.repository.BookmarkRegionRepository;
import com.example.realq.domain.realtime.region.entity.Region;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import com.example.realq.domain.user.entity.User;
import com.example.realq.domain.user.repository.UserRepository;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkRegionService {

    private final BookmarkRegionRepository bookmarkRegionRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public BookmarkRegionCreateResponse registerBookmarkRegion(
            BookmarkRegionCreateRequest requestDto,
            HttpSession session
    ) {
        String slackId = (String) session.getAttribute("slackId");
        Long regionIdToRegister = requestDto.regionId();

        User user = userRepository.findBySlackId(slackId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Region region = regionRepository.findById(regionIdToRegister)
                .orElseThrow(() -> new GlobalException(ErrorCode.REGION_NOT_FOUND));

        Optional<BookmarkRegion> existingBookmarkRegion = bookmarkRegionRepository.findByUserSlackIdAndRegionId(
                slackId,
                regionIdToRegister
        );

        if (existingBookmarkRegion.isPresent()) {
            return BookmarkRegionCreateResponse.toDto(existingBookmarkRegion.get());
        }

        BookmarkRegion bookmarkRegion = BookmarkRegion.toEntity(user, region);

        bookmarkRegionRepository.save(bookmarkRegion);

        return BookmarkRegionCreateResponse.toDto(bookmarkRegion);
    }

    @Transactional(readOnly = true)
    public List<BookmarkRegionReadResponse> readAllBookmarkRegions(HttpSession session) {

        String slackId = (String) session.getAttribute("slackId");

        List<BookmarkRegion> bookmarkRegionList = bookmarkRegionRepository.findByUserSlackId(slackId);

        return bookmarkRegionList.stream().map(BookmarkRegionReadResponse::toDto).toList();
    }

    @Transactional
    public void deleteBookmarkRegion(List<Long> bookmarkRegionIdList) {

        bookmarkRegionIdList.forEach(id -> {
            Optional<BookmarkRegion> existingBookmarkRegion = bookmarkRegionRepository.findById(id);

            if (existingBookmarkRegion.isEmpty()) {
                return;
            }

            bookmarkRegionRepository.deleteById(id);
        });
    }
}