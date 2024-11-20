package com.udemy.star_d_link.study.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/*
 임시 유저 entity
 */
@Entity
@Table(name = "users")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(nullable = false, length = 40)
    private String nickname;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(length = 250)
    private String profileUrl;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String birthDate;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, length = 20)
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyMembers> studyMemberships = new ArrayList<>();

}