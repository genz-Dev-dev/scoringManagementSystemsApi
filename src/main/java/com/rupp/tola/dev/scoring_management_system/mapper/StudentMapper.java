package com.rupp.tola.dev.scoring_management_system.mapper;

import java.util.List;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.utils.StudentCodeGenerateUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rupp.tola.dev.scoring_management_system.service.StudentService;

@Mapper(componentModel = "spring", uses = { StudentService.class, StudentAddressMapper.class })
public interface StudentMapper {

	@Mapping(target = "clazz", ignore = true)
	@Mapping(target = "studentCode" , ignore = true)
    Student toEntity(StudentRequest studentRequest);

	@Mapping(target = "address", source = "address")
	StudentResponse toResponse(Student student);

	List<StudentResponse> toList(List<Student> students);

	@AfterMapping
	default void toStudentCode(@MappingTarget Student student) {
		student.setStudentCode(StudentCodeGenerateUtils.generator());
	}
}
