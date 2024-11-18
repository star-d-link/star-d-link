package com.udemy.star_d_link.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "course_file")
public class CourseReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    String fileUrl;

    @ManyToOne
    CourseReview courseReview;


    @CreationTimestamp
    @Column(updatable = false)
    LocalDate createdAt;
    @UpdateTimestamp
    LocalDate updatedAt;

    public static CourseReviewFile of(String fileUrl, CourseReview courseReview) {
        CourseReviewFile courseReviewFile = CourseReviewFile.builder()
            .fileUrl(fileUrl)
            .courseReview(courseReview)
            .build();
        courseReview.addFile(courseReviewFile);
        return courseReviewFile;
    }
}
