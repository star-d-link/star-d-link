package com.udemy.star_d_link.user.repository;

import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.exception.UserExceptions;
import com.udemy.star_d_link.user.exception.UserTaskException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("다중 저장 테스트")
    @Transactional
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = createUserEntity(i);
            userRepository.save(userEntity);
        }
    }

    @Test
    @DisplayName("저장되지 않은 ID로 검색하여 예외 발생 테스트")
    @Transactional
    public void testException() {
        UserEntity userEntity = createUserEntity(0);
        userRepository.save(userEntity);
        Long savedId = userEntity.getId();

        Assertions.assertThatThrownBy(() -> userRepository.findById(savedId + 1)
                .orElseThrow(UserExceptions.NOT_FOUND::get))
            .isInstanceOf(UserTaskException.class);

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