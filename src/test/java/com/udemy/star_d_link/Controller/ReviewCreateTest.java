package com.udemy.star_d_link.Controller;

import com.udemy.star_d_link.Repository.CourseReviewRepository;
import com.udemy.star_d_link.Service.CourseReviewService;
import com.udemy.star_d_link.course.Dto.CourseReviewCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class ReviewCreateTest {
    @Autowired
    private CourseReviewService courseReviewService;

    @Test
    public void createReviewTest() {

    }
}
