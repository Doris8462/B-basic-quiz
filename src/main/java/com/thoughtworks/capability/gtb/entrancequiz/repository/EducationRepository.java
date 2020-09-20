package com.thoughtworks.capability.gtb.entrancequiz.repository;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {
    List<EducationEntity> findAllByUserId(Long id);
}
