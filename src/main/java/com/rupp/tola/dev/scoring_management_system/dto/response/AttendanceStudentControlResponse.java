package com.rupp.tola.dev.scoring_management_system.dto.response;

import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AttendanceStudentControlResponse {
    private String id;

    private String classId;

    private String subjectId;

    private String studentId;

    private String status;

    private LocalDate createAt;

    private Boolean idDeleted;
}
