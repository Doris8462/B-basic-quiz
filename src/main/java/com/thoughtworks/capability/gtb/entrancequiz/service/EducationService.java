package com.thoughtworks.capability.gtb.entrancequiz.service;

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

    public static Education convertEducationEntityToEducation(EducationEntity educationEntity) {
        return Education.builder().title(educationEntity.getTitle())
                .year(educationEntity.getYear()).userId(educationEntity.getUserId())
                .description(educationEntity.getDescription()).build();
    }

    public static EducationEntity convertEducationToEducationEntity(Education education) {
        return EducationEntity.builder().title(education.getTitle())
                .year(education.getYear()).userId(education.getUserId())
                .description(education.getDescription()).build();
    }

    public List<Education> getEducationByUserId(Long userId) {
        if (userRepository.existsById(userId)) {
            List<EducationEntity> educations = educationRepository.findAllByUserId(userId);
            return educations.stream()
                    .map(education -> convertEducationEntityToEducation(education))
                    .collect(Collectors.toList());
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }

    public Education createEducation(Education education, Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            EducationEntity educationEntity = convertEducationToEducationEntity(education);
            EducationEntity savedEducation = educationRepository.save(educationEntity);
            return convertEducationEntityToEducation(savedEducation);
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }
}
