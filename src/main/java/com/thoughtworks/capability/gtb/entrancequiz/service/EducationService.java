package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.Convert;
import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    private final UserRepository userRepository;

    public EducationService(EducationRepository educationRepository,
                            UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }


    public List<Education> getEducationByUserId(Long userId) {
        if (userRepository.existsById(userId)) {
            List<EducationEntity> educations = educationRepository.findAllByUserId(userId);
            return educations.stream()
                    .map(education -> Convert.convert(education,Education.class))
                    .collect(Collectors.toList());
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }

    public Education createEducation(Education education, Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            EducationEntity educationEntity = Convert.convert(education,EducationEntity.class);
            educationEntity.setUserId(id);
            EducationEntity savedEducation = educationRepository.save(educationEntity);
            return Convert.convert(savedEducation,Education.class);
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }
}
