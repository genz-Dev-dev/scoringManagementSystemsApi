package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceStaffRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceStaffResponse;
import com.rupp.tola.dev.scoring_management_system.entity.AttendanceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceStaffMapper {

    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "reason",source = "reason")
    @Mapping(target = "requestType",source = "requestType")
    AttendanceRequest toEntity(AttendanceStaffRequest attendanceStaffRequest);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "reason",source = "reason")
    @Mapping(target = "requestType",source = "requestType")
    @Mapping(target = "createdAt",source = "createdAt")
    @Mapping(target = "staffId",source = "user.id")
    @Mapping(target = "requestDate",source = "requestDate")
    @Mapping(target = "approvedType",source = "approvedType")
    AttendanceStaffResponse toResponse(AttendanceRequest attendanceRequest);

}
