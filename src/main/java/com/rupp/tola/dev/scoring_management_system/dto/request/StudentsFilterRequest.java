package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsFilterRequest {
    private UUID classId;
    private UUID courseId;
    private UUID departmentId;
    private UUID subjectId;
    private UUID semsterId;
}
