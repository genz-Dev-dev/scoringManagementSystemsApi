package com.rupp.tola.dev.scoring_management_system.dto.response;

import liquibase.exception.DatabaseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;

import java.util.Date;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentsFilterResponse {
        private UUID studentId;
        private String studentCode;
        private String fullName;

        private UUID classId;
        private UUID courseId;
        private UUID departmentId;
        private UUID subjectId;
        private UUID semesterId;
}
