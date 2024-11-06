package com.udemy.star_d_link.user.repository;

import com.udemy.star_d_link.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = createUserEntity(i);
            userRepository.save(userEntity);
        }
    }

    private UserEntity createUserEntity(int num) {
        return UserEntity.builder()
            .username("user" + num)
            .password(passwordEncoder.encode("1234"))
            .nickname("nickname" + num)
            .email("email" + num)
            .phoneNumber("phoneNumber" + num)
            .birthDate("birthDate" + num)
            .region("region" + num)
            .role(num <= 8 ? "USER" : "ADMIN")
            .build();

    }
}