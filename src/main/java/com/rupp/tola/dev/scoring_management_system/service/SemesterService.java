package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.SemesterRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SemesterResponse;

import java.util.List;
import java.util.UUID;

public interface SemesterService {

    SemesterResponse create(SemesterRequest request);

    SemesterResponse update(UUID id , SemesterRequest request);

    void delete(UUID id);

    List<SemesterResponse> getAll();

    SemesterResponse getById(UUID id);

}
