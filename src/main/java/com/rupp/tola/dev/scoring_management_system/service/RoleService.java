package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.RoleRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.RoleResponse;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;

public interface RoleService {

	RoleResponse create(RoleRequest request);
	RoleResponse update(UUID uuid ,RoleRequest request);
	void delete(UUID uuid);
	List<RoleResponse> findAll();
	RoleResponse findById(UUID uuid);
	List<RoleResponse> findByActive(RoleStatus status);

}
