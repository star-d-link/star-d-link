package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleAllUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleSingleUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.ParticipationStatus;
import com.udemy.star_d_link.study.Entity.RecurrenceType;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleParticipationRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyScheduleService {

    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyScheduleParticipationRepository participationRepository;

    @Autowired
    public StudyScheduleService(StudyScheduleRepository studyScheduleRepository, StudyRepository studyRepository,
        StudyScheduleParticipationRepository participationRepository, StudyMemberRepository studyMemberRepository) {
        this.studyScheduleRepository = studyScheduleRepository;
        this.studyRepository = studyRepository;
        this.studyMemberRepository = studyMemberRepository;
        this.participationRepository = participationRepository;}

    public List<StudyScheduleResponseDto> getScheduleList(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));
        List<StudySchedule> studyScheduleList = studyScheduleRepository.findByStudy(study);

        return studyScheduleList.stream()
            .map(StudyScheduleResponseDto::fromEntity)
            .collect(Collectors.toList());
    }

    public StudySchedule addSchedule(Long studyId, StudyScheduleCreateRequestDto requestDto) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        List<StudyMembers> studyMembers = studyMemberRepository.findByStudy(study);
        if (studyMembers == null || studyMembers.isEmpty()) {
            throw new IllegalStateException("스터디에 멤버가 존재하지 않습니다.");
        }

        List<StudySchedule> schedulesToSave = new ArrayList<>();

        if (Boolean.TRUE.equals(requestDto.getIsRecurring())) {
            // 첫 번째 스케줄 생성
            StudySchedule initialSchedule = createInitialSchedule(study, requestDto, null);

            // 첫 번째 스케줄 저장 후 recurrenceGroup 설정
            StudySchedule savedInitialSchedule = studyScheduleRepository.save(initialSchedule);
            Long recurrenceGroup = savedInitialSchedule.getScheduleId(); // 그룹 ID 설정

            // 첫 번째 스케줄의 recurrenceGroup 업데이트
            savedInitialSchedule = savedInitialSchedule.toBuilder()
                .recurrenceGroup(recurrenceGroup)
                .build();
            studyScheduleRepository.save(savedInitialSchedule);

            // 나머지 반복 일정 생성
            LocalDateTime currentScheduleDate = requestDto.getScheduleDate();
            int count = 0;
            while (requestDto.getRecurrenceCount() == null || count < requestDto.getRecurrenceCount() - 1) {
                currentScheduleDate = calculateNextScheduleDate(currentScheduleDate, requestDto.getRecurrenceType());
                StudySchedule newSchedule = createRecurringSchedule(study, requestDto, currentScheduleDate, recurrenceGroup);
                schedulesToSave.add(newSchedule);
                count++;
            }
        } else {
            // 단일 일정 생성
            StudySchedule studySchedule = createInitialSchedule(study, requestDto, null);
            schedulesToSave.add(studySchedule);
        }

        List<StudySchedule> savedSchedules = studyScheduleRepository.saveAll(schedulesToSave);

        // 참여 정보 생성 및 저장
        List<StudyScheduleParticipation> participationToSave = new ArrayList<>();
        for (StudySchedule schedule : savedSchedules) {
            for (StudyMembers member : studyMembers) {
                StudyScheduleParticipation participation = StudyScheduleParticipation.builder()
                    .studySchedule(schedule)
                    .username(member.getUsername())
                    .status(ParticipationStatus.PENDING)
                    .build();
                participationToSave.add(participation);
            }
        }
        participationRepository.saveAll(participationToSave);

        // 첫 번째 스케줄 반환
        return savedSchedules.get(0);

    }
    @Transactional
    public void updateAllSchedule(Long recurrenceGroupId, StudyScheduleAllUpdateRequestDto requestDto) {
        // recurrenceGroupId를 이용해 반복 그룹 전체 조회
        List<StudySchedule> schedules = studyScheduleRepository.findByRecurrenceGroup(recurrenceGroupId);

        if (schedules.isEmpty()) {
            throw new NoSuchElementException("해당 반복 그룹에 대한 스케줄을 찾을 수 없습니다.");
        }

        // 반복 그룹의 모든 스케줄을 업데이트
        List<StudySchedule> updatedSchedules = new ArrayList<>();
        for (StudySchedule schedule : schedules) {
            StudySchedule updatedSchedule = schedule.toBuilder()
                .scheduleTitle(requestDto.getScheduleTitle())
                .scheduleContent(requestDto.getScheduleContent())
                .location(requestDto.getLocation())
                .build();

            updatedSchedules.add(updatedSchedule);
        }

        studyScheduleRepository.saveAll(schedules);
    }

    @Transactional
    public void updateSingleSchedule(Long scheduleId, StudyScheduleSingleUpdateRequestDto requestDto) {
        StudySchedule schedule = studyScheduleRepository.findByScheduleId(scheduleId)
            .orElseThrow(() -> new NoSuchElementException("해당 스케줄을 찾을 수 없습니다."));

        // 개별 스케줄 업데이트
        StudySchedule updatedSchedule = schedule.toBuilder()
            .scheduleTitle(requestDto.getScheduleTitle())
            .scheduleContent(requestDto.getScheduleContent())
            .scheduleDate(requestDto.getScheduleDate())
            .location(requestDto.getLocation())
            .build();
        studyScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId) {

        StudySchedule studySchedule = studyScheduleRepository.findByScheduleId(scheduleId)
            .orElseThrow(() -> new RuntimeException("해당 스케쥴을 찾을 수 없습니다."));

        if (studySchedule.getRecurrenceGroup() != null) {
            // 반복 그룹의 모든 스케줄 삭제
            List<StudySchedule> recurringSchedules = studyScheduleRepository.findByRecurrenceGroup(studySchedule.getRecurrenceGroup());
            studyScheduleRepository.deleteAll(recurringSchedules);
        }
        else {
            // 단일 일정 삭제
            studyScheduleRepository.delete(studySchedule);
        }
    }

    private StudySchedule createInitialSchedule(Study study, StudyScheduleCreateRequestDto requestDto, Long recurrenceGroup) {
        return StudySchedule.builder()
            .study(study)
            .scheduleTitle(requestDto.getScheduleTitle())
            .scheduleContent(requestDto.getScheduleContent())
            .scheduleDate(requestDto.getScheduleDate())
            .location(requestDto.getLocation())
            .recurrenceGroup(recurrenceGroup)
            .build();
    }

    private StudySchedule createRecurringSchedule(Study study, StudyScheduleCreateRequestDto requestDto, LocalDateTime scheduleDate, Long recurrenceGroup) {
        return StudySchedule.builder()
            .study(study)
            .scheduleTitle(requestDto.getScheduleTitle())
            .scheduleContent(requestDto.getScheduleContent())
            .scheduleDate(scheduleDate)
            .location(requestDto.getLocation())
            .recurrenceGroup(recurrenceGroup)
            .build();
    }


    private LocalDateTime calculateNextScheduleDate(LocalDateTime currentDate, RecurrenceType recurrenceType) {
        switch (recurrenceType) {
            case DAILY:
                return currentDate.plusDays(1);
            case WEEKLY:
                return currentDate.plusWeeks(1);
            case MONTHLY:
                return currentDate.plusMonths(1);
            default:
                throw new IllegalArgumentException("잘못된 반복 유형입니다");
        }
    }
}
