package com.udemy.star_d_link;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(StudyRepository studyRepository) {
        return args -> {
            // 200개의 임시 스터디 모집글 생성
            IntStream.rangeClosed(1, 200).forEach(i -> {
                boolean isOnline = i % 3 == 0; // 3의 배수는 온라인

                // 오프라인인 경우 랜덤 좌표 생성
                Double latitude = isOnline ? null : getRandomDouble(33.0, 38.6);
                Double longitude = isOnline ? null : getRandomDouble(124.6, 131.9);

                // Study 객체 생성
                Study study = Study.builder()
                    .username("user" + i)
                    .title("테스트 스터디 모집글 " + i)
                    .content("이것은 테스트용 스터디 모집글입니다. 번호: " + i)
                    .hashtag("#asdf #qwer")
                    .region(isOnline ? "온라인" : "오프라인 지역 " + (i % 5 + 1))
                    .isRecruit(i % 2 == 0)
                    .isOnline(isOnline)
                    .headCount((i % 10) + 1)
                    .latitude(latitude) // 빌더에 좌표 포함
                    .longitude(longitude) // 빌더에 좌표 포함
                    .build();

                // 스터디 저장
                studyRepository.save(study);
            });
        };
    }

    // 대한민국 범위에서 랜덤 좌표 생성
    private double getRandomDouble(double min, double max) {
        return min + (Math.random() * (max - min));
    }
}
