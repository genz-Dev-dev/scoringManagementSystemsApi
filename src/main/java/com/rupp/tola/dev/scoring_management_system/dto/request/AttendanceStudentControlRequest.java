package com.rupp.tola.dev.scoring_management_system.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStudentControlRequest {
	private UUID classId;

	private UUID student;

	private UUID instructorId;

	private UUID subjectId;

	private String status;

	private Boolean idDeleted;
}
