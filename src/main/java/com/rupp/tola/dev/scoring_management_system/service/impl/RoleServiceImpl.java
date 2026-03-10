package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.RoleRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.RoleResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;
import com.rupp.tola.dev.scoring_management_system.mapper.RoleMapper;
import com.rupp.tola.dev.scoring_management_system.repository.RolesRepository;
import org.springframework.stereotype.Service;
import com.rupp.tola.dev.scoring_management_system.service.RoleService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RolesRepository rolesRepository;
	private final RoleMapper roleMapper;
	private final RoleMapper rolesMapper;

	@Override
	public RoleResponse create(RoleRequest request) {
		Roles roles = roleMapper.toEntity(request);
		Roles saved = rolesRepository.save(roles);
		return rolesMapper.toResponse(saved);
	}

	@Override
	public RoleResponse update(UUID uuid, RoleRequest request) {
		return null;
	}

	@Override
	public void delete(UUID uuid) {

	}

	@Override
	public List<RoleResponse> findAll() {
		return List.of();
	}

	@Override
	public RoleResponse findById(UUID uuid) {
		return null;
	}

	@Override
	public List<RoleResponse> findByActive(RoleStatus status) {
		return List.of();
	}
}
