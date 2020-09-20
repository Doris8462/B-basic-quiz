package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (userRepository.existUser(userId)) {
            List<EducationEntity> educations = educationRepository.getEducationsByUserId(userId);
            return educations.stream()
                    .map(education -> convertEducationEntityToEducation(education))
                    .collect(Collectors.toList());
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }

    public Education createEducation(Education education) {
        if (userRepository.existUser(education.getUserId())) {
            EducationEntity educationEntity = convertEducationToEducationEntity(education);
            EducationEntity savedEducation = educationRepository.saveEducation(educationEntity);
            return convertEducationEntityToEducation(savedEducation);
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }
}
