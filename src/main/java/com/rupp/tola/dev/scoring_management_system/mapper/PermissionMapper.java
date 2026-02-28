package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.rupp.tola.dev.scoring_management_system.dto.PermissionDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Permissions;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

	Permissions toPermissions(PermissionDTO permissionDTO);

	PermissionDTO tPermissionDTO(Permissions permissions);
}
