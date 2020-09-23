package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.service.EducationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EducationController.class)
@AutoConfigureJsonTesters
public class EducationControllerTest {

    @MockBean
    private EducationService educationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Education> educationJson;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Education firstEducation;

    @BeforeEach
    public void beforeEach() {
        firstEducation = Education.builder()
                .year(1995L)
                .title("aaa")
                .description("A good guy.")
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(educationService);
    }

    @Nested
    class GetEducationByUserId {
        @Nested
        class WhenUserIdExists {
            @Test
            public void should_return_education_by_userId_with_jsonPath() throws Exception {
                List<Education> educations = new ArrayList<>();
                educations.add(firstEducation);
                when(educationService.getEducationByUserId(1L)).thenReturn(educations);

                mockMvc.perform(get("/users/{id}/educations", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].year", is(1995)));

                verify(educationService).getEducationByUserId(1L);
            }
        }

        @Nested
        class WhenUserIdNotExisted {

            @Test
            public void should_throw_exception_when_user_id_is_not_valid() throws Exception {
                List<Education> educations = new ArrayList<>();
                educations.add(firstEducation);
                when(educationService.getEducationByUserId(1L)).thenReturn(educations);
                mockMvc.perform(get("/users/{id}/educations", "invalidId"))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status", is(400)))
                        .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                        .andExpect(jsonPath("$.message", is("id类型不匹配")));
                verify(educationService, times(0)).getEducationByUserId(1L);
            }

            @Test
            public void should_return_NOT_FOUND() throws Exception {
                when(educationService.getEducationByUserId(123L)).thenThrow(new UserNotExistException(ExceptionEnum.USER_NOT_EXIST));

                mockMvc.perform(get("/users/{id}/educations", 123L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                verify(educationService).getEducationByUserId(123L);
            }
        }
    }

    @Nested
    class CreateEducation {

        @Nested
        class WhenRequestIsValid {

            @Test
            public void should_return_education_when_create_education() throws Exception {
                when(educationService.createEducation( firstEducation,1L))
                        .thenReturn(firstEducation);
                mockMvc.perform(post("/users/{id}/educations", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstEducation)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.year", is(1995)))
                        .andExpect(jsonPath("$.title", is("aaa")))
                        .andExpect(jsonPath("$.description", is("A good guy.")));
                verify(educationService).createEducation(firstEducation,1L);
            }
        }
    }
}
