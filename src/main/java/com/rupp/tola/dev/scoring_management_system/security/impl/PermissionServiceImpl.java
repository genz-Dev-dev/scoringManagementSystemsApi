package com.rupp.tola.dev.scoring_management_system.security.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rupp.tola.dev.scoring_management_system.entity.Permissions;
import com.rupp.tola.dev.scoring_management_system.exception.ApiException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.PermissionRepository;
import com.rupp.tola.dev.scoring_management_system.security.PermissionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

	private final PermissionRepository permissionRepository;

	@Override
	public Permissions createPermissions(Permissions permissions) {
		return permissionRepository.save(permissions);
	}

	@Override
	public Permissions getById(UUID id) {
		return permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("permmsionId id required", id));
	}

	@Override
	public Permissions updatePermissionById(UUID id, Permissions permissionsUpdate) {
		Permissions permissions = getById(id);
		permissions.setName(permissionsUpdate.getName());
		return permissionRepository.save(permissions);
	}

	@Override
	public Permissions updatePermissionsByStatus(UUID id, Boolean status) {

		String message = "status update not found";
		Permissions permissions = permissionRepository.findById(id)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, message));
		permissions.setStatus(status);
		return permissionRepository.save(permissions);
	}

	@Override
	public Optional<Permissions> getByStatus(Boolean status) {
		String message = "status not found";
		Permissions permissions = permissionRepository.findByStatus(status)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, message));
		return Optional.ofNullable(permissions);
	}

}
