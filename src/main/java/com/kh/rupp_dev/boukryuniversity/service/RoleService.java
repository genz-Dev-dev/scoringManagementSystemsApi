package com.kh.rupp_dev.boukryuniversity.service;

import java.util.List;
import java.util.UUID;

import com.kh.rupp_dev.boukryuniversity.dto.request.AssignPermissionRequest;
import com.kh.rupp_dev.boukryuniversity.dto.request.RoleRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.RoleResponse;

public interface RoleService {

	RoleResponse create(RoleRequest request);

	RoleResponse update(UUID uuid ,RoleRequest request);

	List<RoleResponse> findAll();

	RoleResponse findById(UUID uuid);

	void updateStatus(UUID uuid, String status);

	List<RoleResponse> findByActive(String status);

	RoleResponse addPermission(UUID roleId , AssignPermissionRequest request);

	RoleResponse setPermission(UUID roleId , AssignPermissionRequest request);

	void deletePermission(UUID roleId , UUID permissionId);

}
