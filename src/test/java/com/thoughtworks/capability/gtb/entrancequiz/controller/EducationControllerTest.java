package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
