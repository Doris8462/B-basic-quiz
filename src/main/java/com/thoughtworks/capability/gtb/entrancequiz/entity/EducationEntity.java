package com.thoughtworks.capability.gtb.entrancequiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "education")
public class EducationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long year;
    private String title;
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)

    private UserEntity user;
    @Column(name = "user_id")
    private Long userId;
}
