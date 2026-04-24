package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.PermissionRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.PermissionResponse;

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