package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceStudentControlRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceStudentControlResponse;
import com.rupp.tola.dev.scoring_management_system.entity.StudentAttendanceControl;
import com.rupp.tola.dev.scoring_management_system.mapper.AttendanceStudentControlMapper;
import com.rupp.tola.dev.scoring_management_system.service.StudentAttendanceControlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attendance-student")
@RequiredArgsConstructor
public class StudentAttendanceControlController {
	private final StudentAttendanceControlService studentAttendanceControlService;
	private final AttendanceStudentControlMapper attendanceStudentControlMapper;

	@PostMapping()
	public ResponseEntity<?> createBulk(
			@RequestBody @Valid List<AttendanceStudentControlRequest> requests) {

		List<StudentAttendanceControl> entities = requests.stream()
				.map(attendanceStudentControlMapper::toEntity)
				.toList();

		List<StudentAttendanceControl> saved =
				studentAttendanceControlService.create(entities);

		List<AttendanceStudentControlResponse> responses = saved.stream()
				.map(attendanceStudentControlMapper::RESPONSE)
				.toList();

		return ResponseEntity.ok(
				SingleResponse.success("Bulk insert success", responses)
		);
	}

	@GetMapping
	public ResponseEntity<SingleResponse<List<AttendanceStudentControlResponse>>> getAll(){

		List<StudentAttendanceControl> studentAttendanceControls =  studentAttendanceControlService.getAll();
		List<AttendanceStudentControlResponse> attendanceStudentControlResponses = studentAttendanceControls.stream()
				.map(attendanceStudentControlMapper::RESPONSE)
				.toList();
		return ResponseEntity.ok(SingleResponse.success("Data is retried successfully..!!",attendanceStudentControlResponses));
	}
}
