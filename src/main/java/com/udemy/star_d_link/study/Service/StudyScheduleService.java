package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.RecurrenceType;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Mapper.StudyMapper;
import com.udemy.star_d_link.study.Mapper.StudyScheduleMapper;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyScheduleService {

    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyRepository studyRepository;
    private final StudyScheduleMapper studyScheduleMapper;
    @Autowired
    public StudyScheduleService(StudyScheduleRepository studyScheduleRepository, StudyRepository studyRepository,
        StudyMapper studyMapper, StudyScheduleMapper studyScheduleMapper) {
        this.studyScheduleRepository = studyScheduleRepository;
        this.studyRepository = studyRepository;
        this.studyScheduleMapper = studyScheduleMapper;
    }

    public List<StudyScheduleResponseDto> getScheduleList(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));
        List<StudySchedule> studyScheduleList = studyScheduleRepository.findByStudy(study);

        return studyScheduleMapper.toDtoList(studyScheduleList);
    }

    public StudyScheduleResponseDto addSchedule(Long studyId, StudyScheduleCreateRequestDto requestDto) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다"));

        List<StudySchedule> schedulesToSave = new ArrayList<>();

        if (Boolean.TRUE.equals(requestDto.getIsRecurring())) {
            // 첫 번째 스케줄 생성
            StudySchedule initialSchedule = studyScheduleMapper.toInitialEntity(requestDto, study);

            // 첫 번째 스케줄 저장 후 recurrenceGroup 설정
            StudySchedule savedInitialSchedule = studyScheduleRepository.save(initialSchedule);
            Long recurrenceGroup = savedInitialSchedule.getScheduleId(); // 그룹 ID 설정

            // 첫 번째 스케줄의 recurrenceGroup 업데이트를 위해 새로운 객체 생성
            savedInitialSchedule = savedInitialSchedule.toBuilder()
                .recurrenceGroup(recurrenceGroup)
                .build();
            studyScheduleRepository.save(savedInitialSchedule);

            // 나머지 반복 일정 생성
            LocalDateTime currentScheduleDate = requestDto.getScheduleDate();
            int count = 0;
            while (requestDto.getRecurrenceCount() == null || count < requestDto.getRecurrenceCount() - 1) {
                currentScheduleDate = calculateNextScheduleDate(currentScheduleDate, requestDto.getRecurrenceType());
                StudySchedule newSchedule = studyScheduleMapper.toRecurringEntity(initialSchedule, currentScheduleDate, recurrenceGroup);
                schedulesToSave.add(newSchedule);
                count++;
            }
        }
        else {
            // 단일 일정 생성
            StudySchedule studySchedule = studyScheduleMapper.toEntity(requestDto, study);
            schedulesToSave.add(studySchedule);
        }

        List<StudySchedule> savedSchedules = studyScheduleRepository.saveAll(schedulesToSave);
        return studyScheduleMapper.toDto(savedSchedules.get(0));
    }

    public StudySchedule updateSchedule(Long scheduleId, StudyScheduleUpdateRequestDto requestDto) {
        StudySchedule studySchedule = studyScheduleRepository.findByScheduleId(scheduleId)
            .orElseThrow(() -> new RuntimeException("해당 스케쥴을 찾을 수 없습니다."));

        if (studySchedule.getRecurrenceGroup() != null) {
            List<StudySchedule> recurringSchedules = studyScheduleRepository.findByRecurrenceGroup(studySchedule.getRecurrenceGroup());
            for (StudySchedule recurringSchedule : recurringSchedules) {
                studyScheduleMapper.updateScheduleFromDto(recurringSchedule, requestDto);
            }
            studyScheduleRepository.saveAll(recurringSchedules);
        }
        else {
            studyScheduleMapper.updateScheduleFromDto(studySchedule, requestDto);
            studyScheduleRepository.save(studySchedule);
        }

        return studySchedule;

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
