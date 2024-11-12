package com.udemy.star_d_link.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.udemy.star_d_link.Service.CourseReviewService;
import com.udemy.star_d_link.Dto.CourseReviewCreateRequestDto;
import com.udemy.star_d_link.Dto.CourseReviewModifyRequestDto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewCreateTest {
    @Autowired
    private CourseReviewService courseReviewService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "1", roles = "USER")
    public void createReviewTest() throws Exception  {
        CourseReviewCreateRequestDto courseReviewCreateRequestDto = CourseReviewCreateRequestDto.builder()
            .userId(1)
            .title("제목")
            .content("내용")
            .hashtag("해시태그")
            .name("강사명")
            .updatedAt(LocalDate.now())
            .createdAt(LocalDate.now())
            .likeCount(0)
            .postType(1)
            .rating(10)
            .build();

        mockMvc.perform(post("/courseReview/create")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseReviewCreateRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.message").value("글이 작성되었습니다."))
            .andExpect(jsonPath("$.data.title").value("제목"))
            .andExpect(jsonPath("$.data.content").value("내용"))
            .andExpect(jsonPath("$.data.name").value("강사명"))
            .andDo(print());;
    }

    @Test
    @WithMockUser(username = "1", roles = "USER")
    public void updateReviewTest() throws Exception {
        // 수정할 내용
        CourseReviewModifyRequestDto courseReviewModifyRequestDto = CourseReviewModifyRequestDto.builder()
            .title("수정된 제목")
            .content("수정된 내용")
            .hashtag("수정된 해시태그")
            .name("수정된 강사명")
            .updatedAt(LocalDate.now())
            .postType(1)
            .rating(9)
            .build();
        String jsonRequest = objectMapper.writeValueAsString(courseReviewModifyRequestDto);
        System.out.println(jsonRequest);
        System.out.println("dddddddddddddddddd");
        // 실제 수정 테스트
        mockMvc.perform(put("/courseReview/modify/1")  // assuming the review ID is 1
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseReviewModifyRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.message").value("글이 수정되었습니다."))
            .andExpect(jsonPath("$.data.title").value("수정된 제목"))
            .andExpect(jsonPath("$.data.content").value("수정된 내용"))
            .andExpect(jsonPath("$.data.name").value("수정된 강사명"))
            .andDo(print());
    }
}
