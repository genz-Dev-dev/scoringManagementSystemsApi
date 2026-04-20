package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentsFilterResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StudentFilterMapper {

    @Mapping(target = "studentId", source = "id")
    @Mapping(target = "studentCode", source = "studentCode")
    @Mapping(target = "fullName",
            expression = "java(student.getEnFirstName() + \" \" + student.getEnLastName())")

    @Mapping(target = "classId", source = "clazz.id")
    @Mapping(target = "departmentId", source = "clazz.department.id")

    @Mapping(target = "courseId", expression = "java(null)")
    @Mapping(target = "subjectId", expression = "java(null)")
    @Mapping(target = "semesterId", expression = "java(null)")
    static StudentsFilterResponse RESPONSE(Student student) {
        return null;
    }
}
