package com.rupp.tola.dev.scoring_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreResponse {
    private String studentId;
    private String khFirstName;
    private String khLastName;
    private String enFirstName;
    private String enLastName;
    private String gender;
    private String studentCode;
    private String className;
    private String subjectName;
    private String semesterName;
    private Integer Score;
    private Boolean status;
    private LocalDate creationAt;
    private LocalDate updatedAt;

}
