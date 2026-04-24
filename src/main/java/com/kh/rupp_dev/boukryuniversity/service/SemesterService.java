package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.SemesterRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SemesterResponse;

import java.util.List;
import java.util.UUID;

public interface SemesterService {

    SemesterResponse create(SemesterRequest request);

    SemesterResponse update(UUID id , SemesterRequest request);

    void delete(UUID id);

    List<SemesterResponse> getAll();

    SemesterResponse getById(UUID id);

}
