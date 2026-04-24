package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.dto.request.PermissionRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.PermissionResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Permission;
import com.kh.rupp_dev.boukryuniversity.entity.Role;
import com.kh.rupp_dev.boukryuniversity.exception.DuplicateResourceException;
import com.kh.rupp_dev.boukryuniversity.exception.ResourceNotFoundException;
import com.kh.rupp_dev.boukryuniversity.mapper.PermissionMapper;
import com.kh.rupp_dev.boukryuniversity.repository.PermissionRepository;
import com.kh.rupp_dev.boukryuniversity.repository.RoleRepository;
import com.kh.rupp_dev.boukryuniversity.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsByNameAndModule(request.getName(), request.getModule())) {
            throw new DuplicateResourceException("Permission already exists");
        }
        Permission permission = permissionMapper.toEntity(request);
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());
            permission.setRoles(roles);
        }
        Permission saved = permissionRepository.save(permission);
        log.info("Permission created: {}", saved);
        return toResponse(saved);
    }

    @Override
    public PermissionResponse update(UUID id, PermissionRequest request) {
        if (permissionRepository.existsByNameAndModule(request.getName(), request.getModule())) {
            throw new DuplicateResourceException("Permission already exists");
        }
        Permission permission = findByOrThrow(id);
        permissionMapper.updateFromRequest(permission, request);
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());
            permission.setRoles(roles);
        }
        Permission updated = permissionRepository.save(permission);
        return toResponse(updated);
    }

    @Override
    public void delete(UUID id) {
        Permission permission = findByOrThrow(id);
        permission.getRoles().removeIf(role -> role.getPermissions().remove(permission));
        permissionRepository.delete(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PermissionResponse getById(UUID id) {
        Permission permission = findByOrThrow(id);
        return toResponse(permission);
    }

    @Override
    public List<PermissionResponse> findByModule(String module) {
        List<Permission> permissions = permissionRepository.findByModule(module);
        return permissions.stream()
                .map(this::toResponse)
                .toList();
    }

    private Permission findByOrThrow(UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with ID: " + id));
    }

    private PermissionResponse toResponse(Permission permission) {
        var response = permissionMapper.toResponse(permission);
        if (permission.getRoles() != null && !permission.getRoles().isEmpty()) {
            List<UUID> roleIds = permission.getRoles()
                    .stream()
                    .map(Role::getId)
                    .toList();
            response.setRoleIds(roleIds);
        }
        return response;
    }
}