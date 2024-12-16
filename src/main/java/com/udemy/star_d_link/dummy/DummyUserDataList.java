package com.udemy.star_d_link.dummy;

import com.udemy.star_d_link.user.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyUserDataList {

    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> create(int count) {
        return IntStream.range(0, count).mapToObj(i ->
            UserEntity.builder()
                .username("user" + i)
                .password(passwordEncoder.encode("1111"))
                .nickname("nickname" + i)
                .email("email" + i)
                .phoneNumber("phoneNumber" + i)
                .birthDate(LocalDate.now())
                .region("region" + i)
                .role(i < 2 ? "ROLE_ADMIN" : "ROLE_USER")
                .build()
        ).toList();
    }

}
