package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Entity.User;
import com.udemy.star_d_link.study.Repository.StudyScheduleParticipationRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyScheduleParticipationService {
    private final StudyScheduleParticipationRepository participationRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    @Autowired
    public StudyScheduleParticipationService(StudyScheduleParticipationRepository participationRepository,  StudyScheduleRepository studyScheduleRepository
    ) {
        this.participationRepository = participationRepository;
        this.studyScheduleRepository = studyScheduleRepository;
    }

    @Transactional
    public StudyScheduleParticipation addParticipation(User user, ParticipationRequestDto requestDto) {
        StudySchedule schedule = studyScheduleRepository.findById(requestDto.getScheduleId())
            .orElseThrow(() -> new NoSuchElementException("해당 스케줄을 찾을 수 없습니다."));

        // Participation 엔티티 생성
        StudyScheduleParticipation participation = StudyScheduleParticipation.builder()
            .studySchedule(schedule)
            .user(user)
            .status(requestDto.getStatus())
            .build();

        // 생성된 Participation 엔티티 저장
        return participationRepository.save(participation);
    }
}
