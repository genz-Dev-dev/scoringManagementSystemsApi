package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.RoleRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.RoleResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.entity.Users;
import com.rupp.tola.dev.scoring_management_system.mapper.RoleMapper;
import com.rupp.tola.dev.scoring_management_system.repository.RolesRepository;
import com.rupp.tola.dev.scoring_management_system.service.AuthService;
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

	private final RolesRepository rolesRepository;
	private final RoleMapper roleMapper;
	private final AuthService authService;

	@Override
	public RoleResponse create(RoleRequest request) {

		if (rolesRepository.existsByName(request.getName())) {
			log.info("Role already exists with the name {}", request.getName());
			throw new IllegalArgumentException("Role name already exists");
		}

		Roles roles = roleMapper.toEntity(request);
		String roleName = request.getName().toUpperCase();
		if (!roleName.startsWith("ROLE_")) {
			roleName = "ROLE_" + roleName;
		}

		roles.setName(roleName);
		if (request.getUserIds() != null) {
			List<Users> users = request.getUserIds().stream()
					.map(userId -> {
						Users user = authService.getUser(userId);
						if (user.getRoles() != null && !user.getRoles().contains(roles)) {
							user.getRoles().add(roles);
						} else if (user.getRoles() == null) {
							user.setRoles(new ArrayList<>(List.of(roles)));
						}
						return user;
					})
					.collect(Collectors.toCollection(ArrayList::new));
			roles.setUsers(users);
		}
		Roles saved = rolesRepository.save(roles);
		log.info("Role created with id {}", saved.getId());
		return toResponse(saved);
	}

	@Override
	public RoleResponse update(UUID uuid, RoleRequest request) {
		Roles roles = rolesRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + uuid));

		if (request.getName() != null) {
			String roleName = request.getName().toUpperCase();
			if (!roleName.startsWith("ROLE_")) {
				roleName = "ROLE_" + roleName;
			}
			if (rolesRepository.existsByNameAndIdNot(roleName, uuid)) {
				log.info("Role already exists with the name {}", roleName);
				throw new IllegalArgumentException("Role name already exists");
			}
			request.setName(roleName);
		}

		roleMapper.updateFromRequest(roles, request);

		if (request.getUserIds() != null) {

			List<Users> existsUsers = request.getUserIds().stream()
					.map(authService::getUser)
					.collect(Collectors.toCollection(ArrayList::new));

			if (roles.getUsers() != null) {
				roles.getUsers().stream()
						.filter(user -> !existsUsers.contains(user))
						.forEach(user -> {
							if (user.getRoles() != null) {
								user.getRoles().remove(roles);
							}
						});
			}

			existsUsers.forEach(user -> {
				if (user.getRoles() != null && !user.getRoles().contains(roles)) {
					user.getRoles().add(roles);
				} else if (user.getRoles() == null) {
					user.setRoles(new ArrayList<>(List.of(roles)));
				}
			});

			roles.setUsers(existsUsers);
		} else {
			if (roles.getUsers() != null) {
				roles.getUsers().forEach(user -> {
					if (user.getRoles() != null) {
						user.getRoles().remove(roles);
					}
				});
				roles.getUsers().clear();
			}
		}

		Roles saved = rolesRepository.save(roles);
		log.info("Role updated with id {}", saved.getId());
		return toResponse(roles);
	}

	@Override
	public void delete(UUID uuid) {
		Roles roles = rolesRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + uuid));
		log.info("Role deleted with id {}", roles.getId());
		rolesRepository.delete(roles);
	}

	@Override
	public List<RoleResponse> findAll() {
		List<Roles> roles = rolesRepository.findAll();
		log.info("Roles found with all {}", roles);
		return roles.stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public RoleResponse findById(UUID uuid) {
		Roles roles = rolesRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + uuid));
		log.info("Role found with id {}", roles.getId());
		return toResponse(roles);
	}

	@Override
	public List<RoleResponse> findByStatus(String status) {
		List<Roles> roles = rolesRepository.findByStatus(status);
		return roles.stream()
				.map(this::toResponse)
				.toList();
	}

	private RoleResponse toResponse(Roles role) {
		RoleResponse response = roleMapper.toResponse(role);
		List<UUID> uuids = role.getUsers().stream()
				.map(Users::getId)
				.toList();
		response.setUserIds(uuids);
		return response;
	}
}
