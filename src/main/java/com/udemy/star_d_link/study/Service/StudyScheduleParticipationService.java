package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Entity.User;
import com.udemy.star_d_link.study.Mapper.StudyScheduleParticipationMapper;
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
    private final StudyScheduleParticipationMapper participationMapper;
    @Autowired
    public StudyScheduleParticipationService(StudyScheduleParticipationRepository participationRepository,  StudyScheduleRepository studyScheduleRepository,
    StudyScheduleParticipationMapper participationMapper) {
        this.participationRepository = participationRepository;
        this.studyScheduleRepository = studyScheduleRepository;
        this.participationMapper = participationMapper;
    }

    @Transactional
    public StudyScheduleParticipation addParticipation(User user, ParticipationRequestDto requestDto) {
        StudySchedule schedule = studyScheduleRepository.findById(requestDto.getScheduleId())
            .orElseThrow(() -> new NoSuchElementException("해당 스케줄을 찾을 수 없습니다."));

        // Mapper를 이용하여 Participation 엔티티 생성
        StudyScheduleParticipation participation = participationMapper.toEntity(requestDto, schedule, user);

        // 생성된 Participation 엔티티 저장
        StudyScheduleParticipation savedParticipation = participationRepository.save(participation);

        // DTO로 변환하여 반환
        return participationMapper.toDto(savedParticipation);
    }
}
