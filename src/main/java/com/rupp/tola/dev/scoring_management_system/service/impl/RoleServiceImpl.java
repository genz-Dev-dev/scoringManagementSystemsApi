package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.AssignPermissionRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.RoleRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.RoleResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Permission;
import com.rupp.tola.dev.scoring_management_system.entity.Role;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.RoleMapper;
import com.rupp.tola.dev.scoring_management_system.repository.PermissionRepository;
import com.rupp.tola.dev.scoring_management_system.repository.RoleRepository;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rupp.tola.dev.scoring_management_system.service.RoleService;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	private final AuthService authService;
	private final PermissionRepository permissionRepository;

	@Override
	public RoleResponse create(RoleRequest request) {

		if (roleRepository.existsByName(request.getName())) {
			log.info("Role already exists with the name {}", request.getName());
			throw new DuplicateResourceException("Role name already exists");
		}

		Role role = roleMapper.toEntity(request);
		String roleName = request.getName().toUpperCase();
		if (!roleName.startsWith("ROLE_")) {
			roleName = "ROLE_" + roleName;
		}

		role.setName(roleName);
		if (request.getUserIds() != null) {
			Set<User> users = request.getUserIds().stream()
					.map(userId -> {
						User user = authService.getUser(userId);
						if(user != null) {
							user.setRole(role);
						}
						return user;
					})
					.collect(Collectors.toSet());
			role.setUsers(users);
		}
		Role saved = roleRepository.save(role);
		log.info("Role created with id {}", saved.getId());
		return toResponse(saved);
	}

	@Override
	public RoleResponse update(UUID uuid, RoleRequest request) {
		Role role = findByOrThrow(uuid);

		if (request.getName() != null) {
			String roleName = request.getName().toUpperCase();
			if (!roleName.startsWith("ROLE_")) {
				roleName = "ROLE_" + roleName;
			}
			if (roleRepository.existsByNameAndIdNot(roleName, uuid)) {
				log.info("Role already exists with the name {}", roleName);
				throw new IllegalArgumentException("Role name already exists");
			}
			request.setName(roleName);
		}

		roleMapper.updateFromRequest(role, request);

		if(request.getUserIds() != null && !request.getUserIds().isEmpty()) {
			Set<User> users = request.getUserIds()
					.stream()
					.map(authService::getUser)
					.collect(Collectors.toSet());
			role.setUsers(users);
		}else {
			role.setUsers(new HashSet<>());
		}

		Role saved = roleRepository.save(role);
		log.info("Role updated with id {}", saved.getId());
		return toResponse(role);
	}

	@Override
	public List<RoleResponse> findAll() {
		List<Role> roles = roleRepository.findAll();
		log.info("Roles found with all {}", roles);
		return roles.stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public RoleResponse findById(UUID uuid) {
		Role role = findByOrThrow(uuid);
		log.info("Role found with id {}", role.getId());
		return toResponse(role);
	}

	@Override
	public void updateStatus(UUID uuid, String status) {
		Role role = findByOrThrow(uuid);
		role.setStatus(status);
		roleRepository.save(role);
	}

	@Override
	public List<RoleResponse> findByActive(String status) {
		List<Role> roles = roleRepository.findByStatus(status);
		log.info("Roles found with status {}", roles);
		return roles.stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public RoleResponse addPermission(UUID roleId, AssignPermissionRequest request) {

		Role role = findByOrThrow(roleId);
		Set<Permission> permissions = permissionRepository.findByIdIn(request.getPermissionIds());
		role.getPermissions().addAll(permissions);
		Role saved = roleRepository.save(role);
		log.info("Role added with id {}" , saved.getId());
		return toResponse(saved);
	}

	@Override
	public RoleResponse setPermission(UUID roleId, AssignPermissionRequest request) {
		Role role = findByOrThrow(roleId);
		Set<Permission> permissions = permissionRepository.findByIdIn(request.getPermissionIds());
		role.getPermissions().clear();
		role.getPermissions().addAll(permissions);
		log.info("Role added with id {}" , role.getId());
		Role saved = roleRepository
				.save(role);
		return toResponse(saved);
	}

	@Override
	public void deletePermission(UUID roleId, UUID permissionId) {
		Role role = findByOrThrow(roleId);
		role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
		roleRepository.save(role);
	}

	private Role findByOrThrow(UUID roleId) {
		return roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
	}

	private RoleResponse toResponse(Role role) {
		RoleResponse response = roleMapper.toResponse(role);
		if(role.getUsers() != null && !role.getUsers().isEmpty()) {
			List<UUID> uuids = role.getUsers().stream()
					.map(User::getId)
					.toList();
			response.setUserIds(uuids);
		}
		return response;
	}
}
