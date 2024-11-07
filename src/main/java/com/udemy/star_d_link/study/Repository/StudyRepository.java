package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import io.micrometer.common.lang.NonNullApi;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NonNullApi
public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByStudyId(Long studyId);
    Optional<Study> findByUserId(Long UserId);
    Page<Study> findAll(Pageable pageable);
}
