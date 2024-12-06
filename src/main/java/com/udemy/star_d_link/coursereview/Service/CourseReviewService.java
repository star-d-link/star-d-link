package com.udemy.star_d_link.coursereview.Service;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewFile;
import com.udemy.star_d_link.coursereview.Repository.CourseReviewFileRepository;
import com.udemy.star_d_link.coursereview.Repository.CourseReviewRepository;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewCreateRequestDto;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewModifyRequestDto;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseReviewFileRepository courseReviewFileRepository;

    public Page<CourseReview> getList(int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createAt")));
        return courseReviewRepository.findAll(pageable);
    }

    @Transactional
    public CourseReview createCourseReview(
        CourseReviewCreateRequestDto courseReviewCreateRequestDto,
        Long userId /*SiteUser siteUser*/) {
        CourseReview newCourseReview = CourseReview.builder()
            /*.siteUser(siteUser)*/
            .userId(userId) //나중에 삭제
            .likeCount(0)
            .title(courseReviewCreateRequestDto.getTitle())
            .content(courseReviewCreateRequestDto.getContent())
            .hashtag(courseReviewCreateRequestDto.getHashtag())
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now())
            .postType(courseReviewCreateRequestDto.getPostType())
            .name(courseReviewCreateRequestDto.getName())
            .rating(courseReviewCreateRequestDto.getRating())
            .build();
        List<CourseReviewFile> fileList = courseReviewCreateRequestDto.getFileListDto().stream()
            .map(fileUrl -> CourseReviewFile.of(fileUrl.getFileUrl(), newCourseReview)).toList();
        courseReviewFileRepository.saveAll(fileList);

        return courseReviewRepository.save(newCourseReview);
    }

    @Transactional
    public CourseReview modifyCourseReview(
        CourseReviewModifyRequestDto courseReviewModifyRequestDto,
        Long boadrId, Long userId) {

        CourseReview courseReview = courseReviewRepository.findById(boadrId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!courseReview.getUserId().equals(userId)) {
            throw new RuntimeException("작성자만 수정 가능합니다.");
        }

        CourseReview modifiedReview = courseReview.toBuilder()
            .title(courseReviewModifyRequestDto.getTitle())
            .content(courseReviewModifyRequestDto.getContent())
            .hashtag(courseReviewModifyRequestDto.getHashtag())
            .postType(courseReviewModifyRequestDto.getPostType())
            .name(courseReviewModifyRequestDto.getName())
            .rating(courseReviewModifyRequestDto.getRating())
            .updatedAt(LocalDate.now())
            .build();
        List<CourseReviewFile> fileList = courseReviewModifyRequestDto.getFileCreateDtoList()
            .stream()
            .map(fileUrl -> CourseReviewFile.of(fileUrl.getFileUrl(), modifiedReview)).toList();
        courseReviewFileRepository.saveAll(fileList);

        return courseReviewRepository.save(modifiedReview);
    }

    public CourseReview getCourseReviewDetail(Long boardId) {
        CourseReview courseReview = courseReviewRepository.findById(boardId)
            .orElseThrow(() -> new NoSuchElementException("강의 리뷰글이 존재하지 않습니다."));
        return courseReview;
    }

    public void deleteCourseReview(Long boardId, Long userId) {
        CourseReview courseReview = courseReviewRepository.findById(boardId)
            .orElseThrow(() -> new NoSuchElementException("강의 리뷰글이 존재하지 않습니다."));
        if (!courseReview.getUserId().equals(userId)) {
            throw new RuntimeException("작성자만 삭제 가능합니다.");
        }
        this.courseReviewRepository.delete(courseReview);
    }
}
