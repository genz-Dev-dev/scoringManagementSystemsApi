package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.DepartmentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.DepartmentResponse;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    DepartmentResponse create(DepartmentRequest request);

    List<DepartmentResponse> getAll();

    DepartmentResponse getById(UUID id);

    DepartmentResponse update(UUID id, DepartmentRequest request);

    void delete(UUID id);

}

