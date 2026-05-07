package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceStaffRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceStaffResponse;
import com.rupp.tola.dev.scoring_management_system.entity.AttendanceRequest;
import com.rupp.tola.dev.scoring_management_system.mapper.AttendanceStaffMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceRequestDto;
import com.rupp.tola.dev.scoring_management_system.entity.Attendance;
import com.rupp.tola.dev.scoring_management_system.mapper.AttendanceMapper;
import com.rupp.tola.dev.scoring_management_system.service.AttendanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

	private final AttendanceService attendanceService;
	private final AttendanceMapper attendanceMapper;
	private final AttendanceStaffMapper attendanceStaffMapper;
	@PostMapping
	public ResponseEntity<SingleResponse<AttendanceResponse>> create(
			@RequestBody @Valid AttendanceRequestDto attendanceRequest) {

		Attendance attendance = attendanceMapper.toEntity(attendanceRequest);
		attendance = attendanceService.create(attendance);
		AttendanceResponse attendanceResponse = attendanceMapper.toResponse(attendance);
		return ResponseEntity.ok(SingleResponse.success("request for working successfully", attendanceResponse));
	}

	@PostMapping("/staff/attendance/request")
	public ResponseEntity<SingleResponse<AttendanceStaffResponse>> createStaffRequest(@RequestBody @Valid
																					  AttendanceStaffRequest attendanceStaffRequest){
	 	AttendanceRequest request = attendanceStaffMapper.toEntity(attendanceStaffRequest);
		request = attendanceService.createAttendanceStaffRequest(request);
		AttendanceStaffResponse attendanceStaffResponse = attendanceStaffMapper.toResponse(request);
		return ResponseEntity.ok(
				SingleResponse.success("request for working successfully", attendanceStaffResponse)
		);
	}
}
