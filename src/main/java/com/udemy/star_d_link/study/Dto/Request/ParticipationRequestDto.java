package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.ParticipationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    @NotNull
    private Long scheduleId;

    @NotNull
    private ParticipationStatus status;
}
