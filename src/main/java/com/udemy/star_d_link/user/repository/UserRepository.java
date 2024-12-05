package com.udemy.star_d_link.user.repository;

import com.udemy.star_d_link.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT MAX(u.id) FROM UserEntity u")
    Long findMaxId();

    Boolean existsByUsername(String string);
    Boolean existsByEmail(String string);
}
