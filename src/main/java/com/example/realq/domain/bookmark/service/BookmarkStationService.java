package com.example.realq.domain.bookmark.service;

import com.example.realq.domain.bookmark.dto.request.BookmarkStationCreateRequest;
import com.example.realq.domain.bookmark.dto.response.BookmarkStationCreateResponse;
import com.example.realq.domain.bookmark.dto.response.BookmarkStationReadResponse;
import com.example.realq.domain.bookmark.entity.BookmarkStation;
import com.example.realq.domain.bookmark.repository.BookmarkStationRepository;
import com.example.realq.domain.station.entity.Station;
import com.example.realq.domain.station.repository.StationRepository;
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
public class BookmarkStationService {

    private final BookmarkStationRepository bookmarkStationRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Transactional
    public BookmarkStationCreateResponse registerBookmarkStation(
            BookmarkStationCreateRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long stationIdToRegister = requestDto.id();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Station station = stationRepository.findById(stationIdToRegister)
                .orElseThrow(() -> new GlobalException(ErrorCode.STATION_NOT_FOUND));

        Optional<BookmarkStation> existingBookmarkStation = bookmarkStationRepository.findByUserEmailAndStationId(
                email,
                stationIdToRegister
        );

        if (existingBookmarkStation.isPresent()) {
            return BookmarkStationCreateResponse.toDto(existingBookmarkStation.get());
        }

        BookmarkStation bookmarkStation = BookmarkStation.toEntity(user, station);

        bookmarkStationRepository.save(bookmarkStation);

        return BookmarkStationCreateResponse.toDto(bookmarkStation);
    }

    @Transactional(readOnly = true)
    public List<BookmarkStationReadResponse> readAllBookmarkStations(HttpSession session) {

        String email = (String) session.getAttribute("email");

        List<BookmarkStation> bookmarkStationList = bookmarkStationRepository.findByUserEmail(email);

        return bookmarkStationList.stream().map(BookmarkStationReadResponse::toDto).toList();
    }

    @Transactional
    public void deleteBookmarkStation(List<Long> bookmarkStationIdList) {

        bookmarkStationIdList.forEach(id -> {
            Optional<BookmarkStation> existingBookmarkStation = bookmarkStationRepository.findById(id);

            if (existingBookmarkStation.isEmpty()) {
                return;
            }

            bookmarkStationRepository.deleteById(id);
        });
    }
}
