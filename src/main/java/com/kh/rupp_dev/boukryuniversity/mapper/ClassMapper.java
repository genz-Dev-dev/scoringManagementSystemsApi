package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.ClassRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ClassResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Class;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClassMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "department", ignore = true)
	Class toEntity(ClassRequest request);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "department", ignore = true)
	void updateFromRequest(ClassRequest classRequest, @MappingTarget Class clazz);

	@Mapping(target = "departmentId", source = "department.id")
	@Mapping(target = "departmentName" , source = "department.name")
	ClassResponse toResponse(Class clazz);

}
