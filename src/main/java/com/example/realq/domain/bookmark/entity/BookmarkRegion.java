package com.example.realq.domain.bookmark.entity;

import com.example.realq.domain.realtime.region.entity.Region;
import com.example.realq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark_region",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "region_id"})}
)
public class BookmarkRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public static BookmarkRegion toEntity(
            User user,
            Region region
    ) {
        BookmarkRegion bookmarkRegion = new BookmarkRegion();
        bookmarkRegion.user = user;
        bookmarkRegion.region = region;
        return bookmarkRegion;
    }
}
