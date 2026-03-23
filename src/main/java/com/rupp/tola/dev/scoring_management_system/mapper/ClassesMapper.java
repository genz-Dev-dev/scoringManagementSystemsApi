package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassesMapper {
	ClassesMapper INSTANCE = Mappers.getMapper(ClassesMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status" , ignore = true)
	Class toEntity(ClassRequest classRequest);

	@Mapping(target = "id", ignore = true)
	void updateFromRequest(ClassRequest classRequest, @MappingTarget Class entity);

	ClassResponse toResponse(Class entity);
}
