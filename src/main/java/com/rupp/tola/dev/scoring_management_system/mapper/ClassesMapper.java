package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassesMapper {
	ClassesMapper INSTANCE = Mappers.getMapper(ClassesMapper.class);

	@Mapping(target = "id", ignore = true)
	Class toClass(ClassRequest classRequest);

	ClassResponse toClassesResponse(Class entity);
}
