package com.rupp.tola.dev.scoring_management_system.mapper;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

@Mapper(componentModel = "spring", uses = { StudentService.class })
public interface StudentsMapper {
	StudentsMapper INSTANCE = Mappers.getMapper(StudentsMapper.class);

	@Mapping(target = "classes", ignore = true)
	Students toEntity(StudentRequest studentRequest);

	@Mapping(target = "classId", source = "classes.id")
	StudentResponse toResponse(Students students);

	List<StudentResponse> toResponseList(List<Students> students);

	// THIS METHOD SOLVES YOUR ERROR
	default Classes map(UUID value) {
		if (value == null) {
			return null;
		}
		Classes classes = new Classes();
		classes.setId(value);
		return classes;
	}
}
