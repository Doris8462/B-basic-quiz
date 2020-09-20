package com.thoughtworks.capability.gtb.entrancequiz.repository;

import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserAddException;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private static List<UserEntity> users;
    private static AtomicLong atomicId;

    private static final Long ID_INITIAL_VALUE = 1L;

    static {
        users = Collections.synchronizedList(new LinkedList<UserEntity>() {
            {
                add(UserEntity.builder().id(1L)
                        .name("KAMIL")
                        .age(24L)
                        .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                        .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                        .build());
            }
        });
        atomicId = new AtomicLong(ID_INITIAL_VALUE);
    }

    public static List<UserEntity> getUsers() {
        return users;
    }

    public static AtomicLong getAtomicId() {
        return atomicId;
    }

    public UserEntity getUserInfoById(Long id) {
        Optional<UserEntity> userEntity = users.stream()
                .filter(u -> u.getId().equals(id)).findFirst();
        if (userEntity.isPresent()) {
            return userEntity.get();
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }

    public UserEntity saveUser(UserEntity user) {
        user.setId(atomicId.incrementAndGet());
        try {
            users.add(user);
            return user;
        } catch (Exception ex) {
            throw new UserAddException(ExceptionEnum.ADD_USER_EXCEPTION);
        }
    }

    public boolean existUser(Long userId) {
        return users.stream()
                .anyMatch(u -> u.getId().equals(userId));
    }
}
