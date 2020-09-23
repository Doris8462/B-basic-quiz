package com.thoughtworks.capability.gtb.entrancequiz.repository;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EducationRepositoryTest {
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_educations_when_id_exists() {
        UserEntity userEntity = UserEntity.builder()
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build();
        entityManager.persistAndFlush(userEntity);
        entityManager.persistAndFlush(EducationEntity.builder()
                .title("aaa").description("11111")
                .year(1995L).user(userEntity)
                .userId(1L).build());
        List<EducationEntity> educations = educationRepository.findAllByUserId(1L);

        assertThat(educations.size()).isEqualTo(1);
        assertThat(educations.get(0).getTitle()).isEqualTo("aaa");
        assertThat(educations.get(0).getDescription()).isEqualTo("11111");
        assertThat(educations.get(0).getYear()).isEqualTo(1995L);
    }
}
