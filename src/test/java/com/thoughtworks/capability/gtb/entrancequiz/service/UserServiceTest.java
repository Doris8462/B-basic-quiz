package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        userEntity = userEntity.builder()
                .id(123L)
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build();
        user = user.builder()
                .id(12L)
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build();
    }

    @Nested
    class FindById {

        @Nested
        class WhenIdExists {

            @Test
            public void should_return_user() {
                when(userRepository.findById(123L)).thenReturn(Optional.of(userEntity));

                User foundUser = userService.getUserById(123L);

                assertThat(foundUser).isEqualTo(User.builder()
                        .id(123L)
                        .name("Panda")
                        .age(24L)
                        .avatar("http://...")
                        .description("A good guy.")
                        .build());
            }
        }
    }

    @Nested
    class CreateUser {

        @Nested
        class WhenIdExists {

            @Test
            public void should_return_user_when_create_user() {
                userEntity.setName("newName");
                when(userRepository.save(any())).thenReturn(userEntity);
                User user = userService.createUser(UserServiceTest.this.user);
                assertThat(user.getName()).isEqualTo("newName");
            }
        }
    }
}

