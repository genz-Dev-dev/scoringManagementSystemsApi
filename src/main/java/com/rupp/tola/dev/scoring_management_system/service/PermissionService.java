package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.PermissionRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.PermissionResponse;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    PermissionResponse update(UUID id , PermissionRequest request);

    void delete(UUID id);

    List<PermissionResponse> getAll();

    PermissionResponse getById(UUID id);

    List<PermissionResponse> findByModule(String module);
}