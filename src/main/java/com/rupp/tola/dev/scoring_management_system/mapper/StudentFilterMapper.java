package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentsFilterResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentFilterMapper {

    @Mapping(target = "studentId", source = "id")
    @Mapping(target = "studentCode", source = "studentCode")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "className", source = "clazz.name")
    @Mapping(target = "classId", source = "clazz.id")
    @Mapping(target = "departmentId", source = "clazz.department.id")
    @Mapping(target = "khLastName",source = "khLastName")
    @Mapping(target = "khFirstName",source = "khFirstName")
    @Mapping(target = "enFirstName",source = "enFirstName")
    @Mapping(target = "enLastName",source = "enLastName")

    StudentsFilterResponse toResponse(Student student);
}