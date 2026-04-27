package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.PermissionRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.PermissionResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Permission;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    Permission toEntity(PermissionRequest request);

    @Mapping(target = "roleIds" , ignore = true)
    PermissionResponse toResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    void updateFromRequest(@MappingTarget Permission permission, PermissionRequest request);
}
