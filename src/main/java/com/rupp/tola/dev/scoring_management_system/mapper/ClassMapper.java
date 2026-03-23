package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;

@Mapper(componentModel = "spring", uses = { StudentsMapper.class })
public interface ClassMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status" , ignore = true)
//	@Mapping(target = "students" , ignore = true)
	Class toEntity(ClassRequest classRequest);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "students" , ignore = true)
	void updateFromRequest(ClassRequest classRequest, @MappingTarget Class entity);

	@Mapping(target = "students", source = "students")
	ClassResponse toResponse(Class entity);

}
