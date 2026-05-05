package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "startDate", source = "startDate")
	@Mapping(target = "endDate", source = "endDate")
	Attendance toEntity(AttendanceRequestDto attendanceRequest);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "startDate", source = "startDate")
	@Mapping(target = "endDate", source = "endDate")
	@Mapping(target = "workDate", source = "workDate")
	@Mapping(target = "status",source = "status")
	AttendanceResponse toResponse(Attendance attendance);
}
