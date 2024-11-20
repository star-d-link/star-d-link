package com.udemy.star_d_link.study.Repository;


import com.udemy.star_d_link.study.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(Long UserId);
}