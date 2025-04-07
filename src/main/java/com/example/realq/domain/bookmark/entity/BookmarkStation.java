package com.example.realq.domain.bookmark.entity;

import com.example.realq.domain.station.entity.Station;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark_station",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "station_id"})}
)
public class BookmarkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    public static BookmarkStation toEntity(
            User user,
            Station station
    ) {
        BookmarkStation bookmarkStation = new BookmarkStation();
        bookmarkStation.user = user;
        bookmarkStation.station = station;
        return bookmarkStation;
    }
}
