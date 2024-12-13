package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import io.micrometer.common.lang.NonNullApi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
@NonNullApi
public interface StudyRepository extends JpaRepository<Study, Long>,
    QuerydslPredicateExecutor<Study> {
    Optional<Study> findByStudyId(Long studyId);
    List<Study> findByUsername(String username);
    Page<Study> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
