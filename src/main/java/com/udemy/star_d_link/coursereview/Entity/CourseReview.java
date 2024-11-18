package com.udemy.star_d_link.coursereview.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "course_review")
public class CourseReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;


    private Long userId;//나중에 삭제
    /*
    @ManyToOne
    private SiteUser siteUser;*/

    private Integer likeCount;

    @Column(length = 30, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(columnDefinition = "TEXT")
    private String hashtag;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;

    @Column(nullable = false)
    private Integer postType;
    @Column(length = 50, nullable = false)
    private String name;

    private Integer rating;

    @OneToMany(mappedBy = "courseReview", cascade = CascadeType.ALL)
    private List<CourseReviewFile> courseReviewFileList;

    public void addFile(CourseReviewFile courseReviewFile) {
        this.courseReviewFileList.add(courseReviewFile);
    }
}
