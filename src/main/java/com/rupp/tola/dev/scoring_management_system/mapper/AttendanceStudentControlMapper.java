package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rupp.tola.dev.scoring_management_system.dto.request.AttendanceStudentControlRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.AttendanceStudentControlResponse;
import com.rupp.tola.dev.scoring_management_system.entity.StudentAttendanceControl;

@Mapper(componentModel = "spring")
public interface AttendanceStudentControlMapper {
	
	@Mapping(target = "clazz.id" ,source = "classId")
	@Mapping(target = "user.id",source = "instructorId")
	@Mapping(target = "student.id",source = "student")
    @Mapping(target = "subject.id",source = "subjectId")
	@Mapping(target = "status",source = "status")
	StudentAttendanceControl toEntity(AttendanceStudentControlRequest attendanceStudentControlRequest);


    @Mapping(target = "id",source = "id")
    @Mapping(target = "status",source = "status")
    @Mapping(target = "classId",source = "clazz.id")
    @Mapping(target = "subjectId",source = "subject.id")
    @Mapping(target = "studentId",source = "student.id")
    @Mapping(target = "idDeleted",source = "idDeleted")
    AttendanceStudentControlResponse RESPONSE(StudentAttendanceControl studentAttendanceControl);

}
