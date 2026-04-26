package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.SubjectRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SubjectResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Subject;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "code" , ignore = true)
    @Mapping(target = "thumbnail" , ignore = true)
    Subject toEntity(SubjectRequest request);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName" , source = "department.name")
    SubjectResponse toResponse(Subject subject);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "code" , ignore = true)
    @Mapping(target = "thumbnail" , ignore = true)
    void updateFromRequest(SubjectRequest request, @MappingTarget Subject subject);
}
