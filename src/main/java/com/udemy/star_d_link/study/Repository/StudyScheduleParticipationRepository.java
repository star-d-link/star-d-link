package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyScheduleParticipationRepository extends JpaRepository<StudyScheduleParticipation, Long> {
    List<StudyScheduleParticipation> findByStudySchedule_ScheduleId(Long scheduleId);
    Optional<StudyScheduleParticipation> findByStudySchedule_ScheduleIdAndUsername(Long studyScheduleId, String username);
}
