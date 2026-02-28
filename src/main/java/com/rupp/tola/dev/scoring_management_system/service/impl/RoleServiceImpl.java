package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.exception.ApiException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.RolesRepository;
import com.rupp.tola.dev.scoring_management_system.service.RoleService;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RolesRepository rolesRepository;

	@Override
	public Roles createRoles(Roles roles) {
		return rolesRepository.save(roles);
	}

	@Override
	public Roles getById(UUID id) {
		return rolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("roleId is require", id));
	}

	@Override
	public List<Roles> roles() {
		return rolesRepository.findAll();
	}

	@Override
	public Roles editRole(UUID id, Roles roleUpdateRole) {
		Roles roles = getById(id);
		roles.setName(roleUpdateRole.getName());
		return rolesRepository.save(roles);
	}

	@Override
	public List<Roles> findByActive(Boolean status) {

		return rolesRepository.findByStatus(status);
	}

	@Override
	public Roles updateStatus(UUID id, Boolean active) {
		Roles roles = rolesRepository.findById(id)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "roleid is null"));
		roles.setStatus(active);
		return rolesRepository.save(roles);
	}

}
