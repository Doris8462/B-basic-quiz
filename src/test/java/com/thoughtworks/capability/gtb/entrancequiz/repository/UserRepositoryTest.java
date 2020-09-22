package com.thoughtworks.capability.gtb.entrancequiz.repository;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_user_when_id_exists() {
        entityManager.persistAndFlush(UserEntity.builder()
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build());

        Optional<UserEntity> found = userRepository.findById(1L);

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(UserEntity.builder()
                .id(1L)
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build());
    }
}
