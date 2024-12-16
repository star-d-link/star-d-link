package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyScheduleParticipationRepository extends JpaRepository<StudyScheduleParticipation, Long> {

}
