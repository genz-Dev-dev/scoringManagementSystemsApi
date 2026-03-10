package com.rupp.tola.dev.scoring_management_system.service;

import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Permissions;

public interface PermissionService {
	Permissions createPermissions(Permissions permissions);

	Permissions getById(UUID id);

	Permissions updatePermissionById(UUID id, Permissions permissionsUpdate);

	Permissions updatePermissionsByStatus(UUID id, Boolean status);

	Optional<Permissions> getByStatus(Boolean status);
}
