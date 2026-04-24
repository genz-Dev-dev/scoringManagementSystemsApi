package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.RoleRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.RoleResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "users" , ignore = true)
    Role toEntity(RoleRequest request);

    @Mapping(target = "userIds" , ignore = true)
    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateFromRequest(@MappingTarget Role role, RoleRequest request);
}