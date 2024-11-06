package com.udemy.star_d_link.dummy;

import com.udemy.star_d_link.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DummyDataLoader {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner loadDummyData(DummyUserDataList dummyUserDataList) {
        return args -> {
            userRepository.saveAll(dummyUserDataList.create(10));
        };
    }
}
