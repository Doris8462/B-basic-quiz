package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        return Convert.convert(userEntity,User.class);
    }

    public User createUser(User user) {
        UserEntity userEntity = Convert.convert(user,UserEntity.class);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return Convert.convert(savedUserEntity,User.class);
    }
}
