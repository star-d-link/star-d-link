package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    Optional<StudySchedule> findByScheduleId(long scheduleId);
    List<StudySchedule> findByStudy(Study study);
    List<StudySchedule> findByRecurrenceGroup(long recurrenceGroup);
}
