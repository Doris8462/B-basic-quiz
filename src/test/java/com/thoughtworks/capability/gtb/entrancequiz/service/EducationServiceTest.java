package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EducationServiceTest {

    private EducationService educationService;
    @Mock
    private EducationRepository educationRepository;
    private EducationEntity educationEntity;
    private Education education;
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        educationService = new EducationService(educationRepository, userRepository);
        educationEntity = EducationEntity.builder()
                .id(1L)
                .userId(1L)
                .title("Panda")
                .year(2000L)
                .description("A good guy.")
                .build();
        education = Education.builder()
                .userId(1L)
                .title("Panda")
                .year(2000L)
                .description("A good guy.")
                .build();
    }

    @Nested
    class FindById {

        @Nested
        class WhenIdExists {

            @Test
            public void should_return_educations_when_user_exist() {
                List<EducationEntity> educations = new ArrayList<>();
                educations.add(educationEntity);
                when(userRepository.existsById(1L)).thenReturn(true);
                when(educationRepository.findAllByUserId(1L)).thenReturn(educations);
                List<Education> educationList = educationService.getEducationByUserId(1L);
                assertThat(educationList.get(0).getTitle()).isEqualTo("Panda");
                assertThat(educationList.get(0).getYear()).isEqualTo(2000L);
            }
        }

        @Nested
        class WhenIdNotExists {
            @Test
            public void should_throw_exception_when_user_not_exist() {
                when(userRepository.existsById(1L)).thenReturn(false);
                UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                        () -> educationService.getEducationByUserId(1L),
                        "Expected doThing() to throw, but it didn't");
                assertEquals("user is not exist",
                        userNotExistException.getExceptionEnum().getMessage());
            }
        }
    }

    @Nested
    class SaveEducation {
        @Test
        public void should_return_education_when_created() {
            UserEntity user = UserEntity.builder().id(1L)
                    .name("Panda")
                    .age(24L)
                    .avatar("http://...")
                    .description("A good guy.")
                    .build();
            Optional<UserEntity> userEntity = Optional.of(user);
            when(userRepository.findById(1L)).thenReturn(userEntity);
            educationEntity.setDescription("created");
            when(educationRepository.save(any())).thenReturn(educationEntity);
            Education education = educationService
                    .createEducation(EducationServiceTest.this.education, 1L);
            assertEquals("created", education.getDescription());
        }
    }
}
