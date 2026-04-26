package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.PermissionRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.PermissionResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Permission;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "status" , ignore = true)
    Permission toEntity(PermissionRequest request);

    @Mapping(target = "roleIds" , ignore = true)
    PermissionResponse toResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "status" , ignore = true)
    void updateFromRequest(@MappingTarget Permission permission, PermissionRequest request);
}
