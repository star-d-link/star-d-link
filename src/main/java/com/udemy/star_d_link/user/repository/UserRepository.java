package com.udemy.star_d_link.user.repository;

import com.udemy.star_d_link.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
