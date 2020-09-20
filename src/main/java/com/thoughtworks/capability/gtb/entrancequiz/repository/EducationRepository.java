package com.thoughtworks.capability.gtb.entrancequiz.repository;

import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.EducationException;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EducationRepository {

    private static List<EducationEntity> educations;

    static {
        educations = Collections.synchronizedList(new LinkedList<EducationEntity>() {
            {
                add(EducationEntity
                        .builder().userId(1L)
                        .title("I was born in Katowice")
                        .year(1990L)
                        .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sapiente, exercitationem, totam, dolores iste dolore est aut modi.")
                        .build());
                add(EducationEntity
                        .builder().userId(1L)
                        .title("Secondary school specializing in artistic")
                        .year(2005L)
                        .description("Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                        .build());
                add(EducationEntity
                        .builder().userId(1L)
                        .title("First level graduation in Graphic Design")
                        .year(2009L)
                        .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
                        .build());
                add(EducationEntity
                        .builder().userId(1L)
                        .title("Second level graduation in Graphic Design")
                        .year(2012L)
                        .description("Ducimus, aliquam tempore autem itaque et accusantium!  ")
                        .build());
            }
        });
    }

    public static List<EducationEntity> getEducations() {
        return educations;
    }

    public EducationEntity saveEducation(EducationEntity educationEntity) {
        try {
            educations.add(educationEntity);
            return educationEntity;
        } catch (Exception ex) {
            throw new EducationException(ExceptionEnum.ADD_EDUCATION_EXCEPTION);
        }
    }

    public List<EducationEntity> getEducationsByUserId(Long userId) {
        return educations.stream()
                .filter(e -> e.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
