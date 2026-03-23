package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentAddressRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentAddressResponse;
import com.rupp.tola.dev.scoring_management_system.entity.StudentAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentAddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    StudentAddress toEntity(StudentAddressRequest request);

    StudentAddressResponse toResponse(StudentAddress entity);
}
