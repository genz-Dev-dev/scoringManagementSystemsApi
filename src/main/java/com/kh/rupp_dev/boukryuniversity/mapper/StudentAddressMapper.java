package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.StudentAddressRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentAddressResponse;
import com.kh.rupp_dev.boukryuniversity.entity.StudentAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentAddressMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "student", ignore = true)
    StudentAddress toEntity(StudentAddressRequest request);

    StudentAddressResponse toResponse(StudentAddress entity);

    @Mapping(target = "student", ignore = true)
    void updateFromRequest(StudentAddressRequest request, @MappingTarget StudentAddress entity);
}
