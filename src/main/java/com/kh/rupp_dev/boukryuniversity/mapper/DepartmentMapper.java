package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.DepartmentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.DepartmentResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "subjects" , ignore = true)
    Department toEntity(DepartmentRequest request);

    @Mapping(target = "thumbnail", source = "thumbnail")
    DepartmentResponse toResponse(Department department);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "subjects" , ignore = true)
    void updateFromRequest(DepartmentRequest request, @MappingTarget Department department);
}
