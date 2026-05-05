package com.rupp.tola.dev.scoring_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentsFilterResponse {
        private UUID studentId;
        private String studentCode;
        private String enFirstName;
        private String enLastName;
        private String khFirstName;
        private String khLastName;
        private String gender;
        private UUID classId;
        private String className;
        private UUID departmentId;
}