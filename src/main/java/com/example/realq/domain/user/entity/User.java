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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String slackId;

    public static User toEntity(
            String email,
            String password,
            String slackId
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.slackId = slackId;
        return user;
    }
}