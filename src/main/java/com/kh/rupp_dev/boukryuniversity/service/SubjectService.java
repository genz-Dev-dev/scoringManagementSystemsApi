package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.SubjectRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SubjectResponse;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    SubjectResponse create(SubjectRequest request);

    List<SubjectResponse> getAll();

    SubjectResponse getById(UUID id);

    SubjectResponse update(UUID id, SubjectRequest request);

    void delete(UUID id);
}
