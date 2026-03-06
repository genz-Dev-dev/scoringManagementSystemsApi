package com.rupp.tola.dev.scoring_management_system.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.dto.StudentsDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Classes;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

@Mapper(componentModel = "spring", uses = { StudentService.class })
public interface StudentsMapper {
	StudentsMapper iNSTANCE = Mappers.getMapper(StudentsMapper.class);

//	@Mapping(target = "classes", source = "classId")
//	Students toStudents(StudentsDTO studentsDTO);
	
	@Mapping(target = "classes", source = "classId")
	Students toStudents(StudentsDTO studentsDTO);

	@Mapping(target = "classId", source = "classes.id")
	StudentsDTO toStudentsDTO(Students students);

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
