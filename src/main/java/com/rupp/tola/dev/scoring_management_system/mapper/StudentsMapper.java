package com.rupp.tola.dev.scoring_management_system.mapper;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.service.StudentService;

@Mapper(componentModel = "spring", uses = { StudentService.class })
public interface StudentsMapper {
	StudentsMapper INSTANCE = Mappers.getMapper(StudentsMapper.class);

	@Mapping(target = "clazz", ignore = true)
    Student toEntity(StudentRequest studentRequest);

	@Mapping(target = "classId", source = "clazz.id")
	StudentResponse toResponse(Student student);

	List<StudentResponse> toResponseList(List<Student> students);

	// THIS METHOD SOLVES YOUR ERROR
	default Class map(UUID value) {
		if (value == null) {
			return null;
		}
		Class aClass = new Class();
		aClass.setId(value);
		return aClass;
	}
}
