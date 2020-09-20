package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserEntity convertUserToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId()).age(user.getAge())
                .avatar(user.getAvatar()).description(user.getDescription())
                .name(user.getName()).build();
    }

    public static User convertUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId()).age(userEntity.getAge())
                .avatar(userEntity.getAvatar()).description(userEntity.getDescription())
                .name(userEntity.getName()).build();
    }

    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        return convertUserEntityToUser(userEntity);
    }

    public User createUser(User user) {
        UserEntity userEntity = convertUserToUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return convertUserEntityToUser(savedUserEntity);
    }
}
