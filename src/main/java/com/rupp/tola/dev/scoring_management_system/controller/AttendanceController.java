package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceResponse;
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

	@PostMapping("")
	public ResponseEntity<SingleResponse<AttendanceResponse>> create(
			@RequestBody @Valid AttendanceRequestDto attendanceRequest) {

		Attendance attendance = attendanceMapper.toEntity(attendanceRequest);
		attendance = attendanceService.create(attendance);

		AttendanceResponse attendanceResponse = attendanceMapper.toResponse(attendance);

		return ResponseEntity.ok(SingleResponse.success("request for working successfully", attendanceResponse));
	}
}
