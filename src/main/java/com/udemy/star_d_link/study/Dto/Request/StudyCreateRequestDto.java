package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.Study;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreateRequestDto {
    @NotNull
    private String username;

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String content;
    private String hashtag;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private Integer headCount;
    private LocalDate createDate;

    public Study toEntity(String username) {
        return Study.builder()
            .title(this.title)
            .content(this.content)
            .hashtag(this.hashtag)
            .isRecruit(this.isRecruit)
            .region(this.region)
            .isOnline(this.isOnline)
            .headCount(this.headCount)
            .createDate(this.createDate)
            .username(username)
            .build();
    }
}
