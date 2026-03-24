package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;

import java.util.List;

@Mapper(componentModel = "spring", uses = { StudentMapper.class})
public interface ClassMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status" , ignore = true)
	Class toEntity(ClassRequest request);

	@Mapping(target = "id", ignore = true)
	void updateFromRequest(ClassRequest classRequest, @MappingTarget Class clazz);

	@Mapping(target = "students", source = "students")
	ClassResponse toResponse(Class clazz);

}
