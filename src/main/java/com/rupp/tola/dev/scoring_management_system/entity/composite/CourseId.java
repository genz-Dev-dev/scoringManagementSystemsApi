package com.rupp.tola.dev.scoring_management_system.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseId implements Serializable {

    @Column(name = "semester_id")
    private UUID semesterId;

    @Column(name = "subject_id")
    private UUID subjectId;

}
