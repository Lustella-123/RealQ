package com.example.realq.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String slackId;

    @Column(nullable = false)
    private String password;

    public static User toEntity(
            String password,
            String slackId
    ) {
        User user = new User();
        user.password = password;
        user.slackId = slackId;
        return user;
    }
}