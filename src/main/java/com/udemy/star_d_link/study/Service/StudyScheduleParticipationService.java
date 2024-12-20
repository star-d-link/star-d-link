package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Repository.StudyScheduleParticipationRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleRepository;
import java.util.List;
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
    public StudyScheduleParticipation updateParticipation(Long scheduleId, String username, ParticipationRequestDto requestDto) {
        StudyScheduleParticipation participation = participationRepository
            .findByStudySchedule_ScheduleIdAndUsername(scheduleId, username)
            .orElseThrow(() -> new NoSuchElementException("해당 참여 정보를 찾을 수 없습니다."));

        if (!participation.getUsername().equals(username)) {
            throw new UnauthorizedException("해당 참여 정보를 수정할 권한이 없습니다.");
        }

        StudyScheduleParticipation updatedParticipation = participation.toBuilder()
            .status(requestDto.getStatus()) // 변경할 값만 설정
            .build();

        return participationRepository.save(updatedParticipation);
    }

    @Transactional(readOnly = true)
    public List<StudyScheduleParticipation> getParticipation(Long scheduleId) {
        return participationRepository.findByStudySchedule_ScheduleId(scheduleId);
    }
}
