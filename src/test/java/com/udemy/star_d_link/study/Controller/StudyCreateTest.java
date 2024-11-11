package com.udemy.star_d_link.study.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Service.StudyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class StudyCreateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudyService studyService;

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    public void createStudyTest() throws Exception {
        // Given: 스터디 작성에 필요한 DTO 생성
        StudyCreateRequestDto requestDto = StudyCreateRequestDto.builder()
            .userId(1L) // 테스트용 임시 userId
            .title("테스트 스터디 제목")
            .content("테스트 스터디 내용")
            .hashtag("#테스트")
            .isRecruit(true)
            .region("서울")
            .isOnline(true)
            .headCount(5)
            .build();

        // When & Then: POST 요청으로 스터디 생성 테스트
        mockMvc.perform(post("/study/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is("작성이 완료되었습니다.")))
            .andExpect(jsonPath("$.data.title", is("테스트 스터디 제목")))
            .andDo(print());
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    public void createStudyWithInvalidDataTest() throws Exception {
        // Given: 필수 필드인 title을 null로 설정하여 유효성 검사 실패 유도
        StudyCreateRequestDto requestDto = StudyCreateRequestDto.builder()
            .userId(1L)
            .title(null) // title이 null
            .content("테스트 스터디 내용")
            .hashtag("#테스트")
            .isRecruit(true)
            .region("서울")
            .isOnline(true)
            .headCount(5)
            .build();

        // When & Then: POST 요청으로 스터디 생성 테스트, 잘못된 입력 데이터로 인해 Bad Request (400) 예상
        mockMvc.perform(post("/study/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest()) // 유효성 검사 실패로 인해 400 Bad Request
            .andDo(print());
    }
}
