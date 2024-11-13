package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Mapper.StudyMapper;
import com.udemy.star_d_link.study.Mapper.StudyScheduleMapper;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.study.Repository.StudyScheduleRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyScheduleService {

    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;
    private final StudyScheduleMapper studyScheduleMapper;
    @Autowired
    public StudyScheduleService(StudyScheduleRepository studyScheduleRepository, StudyRepository studyRepository,
        StudyMapper studyMapper, StudyScheduleMapper studyScheduleMapper) {
        this.studyScheduleRepository = studyScheduleRepository;
        this.studyRepository = studyRepository;
        this.studyMapper = studyMapper;
        this.studyScheduleMapper = studyScheduleMapper;
    }

    public StudyScheduleResponseDto addSchedule(Long studyId, StudyScheduleRequestDto requestDto) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudySchedule studySchedule = studyScheduleMapper.toEntity(requestDto, study);
        StudySchedule saveSchedule = studyScheduleRepository.save(studySchedule);

        return studyScheduleMapper.toDto(saveSchedule);
    }
}
